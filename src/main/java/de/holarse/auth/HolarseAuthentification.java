package de.holarse.auth;

import de.holarse.backend.db.PasswordType;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HolarseAuthentification {

    Logger logger = LoggerFactory.getLogger(HolarseAuthentification.class);
    
    @Autowired
    private UserRepository ur;

    public boolean auth(final User user, final String username, final String password) throws NoSuchAlgorithmException {
        if (user == null || user.getPasswordType() == null) {
            throw new IllegalArgumentException("User oder Passworttyp ist null");
        }

        switch (user.getPasswordType()) {
            case MD5:
                final boolean result = authDrupal(user, username, password);
                if (result) {
                    migrate(user, password);
                }
                return result;
            case BCRYPT:
                return authHolacms3(user, username, password);
        }
        
        return false;
    }

    protected void migrate(final User user, final String password) throws NoSuchAlgorithmException {
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, SecureRandom.getInstanceStrong());
        
        user.setPasswordType(PasswordType.BCRYPT);
        user.setDigest(passwordEncoder.encode(password));
        
        ur.saveAndFlush(user);
        logger.debug("Migration of user {} complete", new Object[] {user.getLogin()});
    }

    protected boolean authDrupal(final User user, final String username, final String password) {
        logger.debug("Authentifizierung über Drupal Credentials");
        return user.getDigest().equals(DigestUtils.md5Hex(password));
    }

    protected boolean authHolacms3(final User user, final String username, final String password) throws NoSuchAlgorithmException{
        logger.debug("Authentifizierung über BCrypt Credentials");
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10, SecureRandom.getInstanceStrong());
        return passwordEncoder.matches(password, user.getDigest());
    }

}
