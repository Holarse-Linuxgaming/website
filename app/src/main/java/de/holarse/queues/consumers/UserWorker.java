package de.holarse.queues.consumers;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserData;
import de.holarse.backend.db.UserStatus;
import de.holarse.backend.db.repositories.RoleRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.types.PasswordType;
import static de.holarse.config.JmsQueueTypes.*;
import de.holarse.config.RoleUserTypes;
import de.holarse.web.services.SlugService;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class UserWorker {

    private final static transient Logger log = LoggerFactory.getLogger(UserWorker.class);

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SlugService slugService;

    @JmsListener(destination = QUEUE_IMPORTS_USERS)
    public void importUsers(final de.holarse.backend.api.User queueEntry) {
        // Gibt es einen solchen User bereits? Dann updaten, sonst anlegen
        User user = userRepository.findByLogin(queueEntry.getLogin());
        if (user == null) {
            user = new User();
        }
        
        user.setCreated(queueEntry.getCreated().toInstant().atOffset(ZoneOffset.UTC));
        user.setLogin(queueEntry.getLogin());
        user.setEmail(queueEntry.getEmail());
        
        user.setDrupalId(queueEntry.getUid().intValue());
        if (queueEntry.getPassword().getType().equalsIgnoreCase("md5")) {
            user.setHashType(PasswordType.md5);
        } else {
            user.setHashType(PasswordType.bcrypt);
        }
        user.setDigest(queueEntry.getPassword().getDigest());
        
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        
        // Standardrolle ist noch nicht vorhanden
        if (user.getRoles().stream().filter(r -> r.getCode().equalsIgnoreCase(RoleUserTypes.ROLE_USER)).count() == 0) {
            // Also anlegen
            user.getRoles().add(roleRepository.findByCode("USER"));
        }
        
        // Rollen aus Import anlegen
        for (final de.holarse.backend.api.Role r : queueEntry.getRoles()) {
            user.getRoles().add(roleRepository.findByCode(r.getValue()));
        }        

        // Gibt es ein UserData?
        if (user.getUserData() == null) {
            final UserData userData = new UserData();
            user.setUserData(userData);
        }
        user.getUserData().setAvatar(queueEntry.getAvatar());
        user.getUserData().setSignature(queueEntry.getSignature());
        
        // Gibt es ein UserStatus?
        if (user.getStatus() != null) {
            final UserStatus userStatus = new UserStatus();
            userStatus.setCreated(OffsetDateTime.now());
            userStatus.setFailedLogins(0);
            userStatus.setLocked(false);
            userStatus.setVerified(true);
            userStatus.setImported(OffsetDateTime.now());
            user.setStatus(userStatus);
        }
        
        userRepository.saveAndFlush(user);
    }

}
