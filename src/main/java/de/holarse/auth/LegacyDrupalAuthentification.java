package de.holarse.auth;

import de.holarse.backend.db.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class LegacyDrupalAuthentification {
 
    public boolean auth(final User user, final String username, final String password) {
        return user.getPassword().equals(DigestUtils.md5Hex(password));
    }
    
}
