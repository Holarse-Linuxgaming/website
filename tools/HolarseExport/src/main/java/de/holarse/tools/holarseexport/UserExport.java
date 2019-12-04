package de.holarse.tools.holarseexport;

import de.holarse.backend.export.Password;
import de.holarse.backend.export.Role;
import de.holarse.backend.export.User;
import static de.holarse.tools.holarseexport.ExportMain.log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserExport implements Export {

    final static Logger log = Logger.getLogger(UserExport.class.getName());          

    // nur user, die sich schonmal eingeloggt haben
    final static String USERS_QUERY = "select u.uid, u.name, u.pass, u.mail, us.signature, from_unixtime(u.created) as created, u.login, u.status, u.picture from users u left join users_signature us on us.uid = u.uid where u.uid > 0 and u.login <> 0 and u.access <> 0";        
    
    final static String ROLES_QUERY = "select u.name as username, r.name as rolename from users_roles ur inner join users u on u.uid = ur.uid inner join role r on r.rid = ur.rid";
    
    protected Map<String, List<String>> roleMaps = new HashMap<>(50);
    
    protected void buildRoleMap(final Connection c) throws Exception {
        roleMaps.clear();
        
        final PreparedStatement p = c.prepareStatement(ROLES_QUERY);
        try (final ResultSet result = p.executeQuery()) {
            while(result.next()) {
                String username = result.getString("username");
                String rolename = result.getString("rolename").toUpperCase();
                
                if (rolename.equals("ADMINISTRATOR")) {
                    rolename = "ADMIN";
                }
                
                roleMaps.putIfAbsent(username, new ArrayList<>());
                roleMaps.get(username).add(rolename);
            }
        }
    }
    
    @Override
    public void export(Connection c) throws Exception {
        long start = System.currentTimeMillis();
        
        buildRoleMap(c);
        
        final PreparedStatement p = c.prepareStatement(USERS_QUERY);
        int count = 0;
        try (final ResultSet result = p.executeQuery()) {
            while(result.next()) {
                String created = result.getString("created");
                if (created.equals("0")) {
                    continue;
                }
                
                User user = new User();
                user.setUid(result.getLong("uid"));
                user.setLogin(result.getString("name"));
                user.setEmail(result.getString("mail"));
                user.setCreated(new Date(result.getTimestamp("created").getTime()));
                user.setAvatar(result.getString("picture"));
                user.setSignature(result.getString("signature"));
                user.setLocked(!result.getBoolean("status"));
                
                Password password = new Password();
                password.setType("MD5");
                password.setDigest(result.getString("pass"));
                
                user.setPassword(password);
                
                if (roleMaps.containsKey(user.getLogin())) {
                    user.setRoles( roleMaps.get(user.getLogin()).stream().map(r -> new Role(r)).collect(Collectors.toList()) );
                }
               
                ExportWriter.writeXml(user, "user", user.getUid());
                count++;
            }
        }
        
        log.log(Level.INFO, "Complete after {0} ms.", System.currentTimeMillis() - start);
    }    
}
