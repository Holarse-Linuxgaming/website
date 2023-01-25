package de.holarse.web.services;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserStatus;
import de.holarse.backend.types.PasswordType;
import de.holarse.web.controller.commands.RegisterForm;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    
    public User createUnverifiedUser(final RegisterForm registerForm) {
        final User user = new User();
        user.setLogin(registerForm.getUsername());
        user.setEmail(registerForm.getEmail());
        user.setHashType(PasswordType.bcrypt);
        
        final UserStatus userStatus = new UserStatus();
        userStatus.setCreated(OffsetDateTime.now());
        userStatus.setFailedLogins(0);
        userStatus.setLocked(false);
        userStatus.setVerified(false);
        // TODO Verified-Hash erzeugen und setzen
        user.setUserStatus(userStatus);        
        return user;
    }
    
    public String generateVerificationHash(final String username) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("HOLARSE");
        buffer.append("+++");
        buffer.append(username);
        buffer.append("+++");
        buffer.append(OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE));
        
        return DigestUtils.sha256Hex(buffer.toString());
    }

}
