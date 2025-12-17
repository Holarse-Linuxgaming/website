package de.holarse.web.services;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserData;
import de.holarse.backend.db.UserStatus;
import de.holarse.backend.types.PasswordType;
import de.holarse.web.controller.commands.RegisterForm;
import java.time.OffsetDateTime;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    
    @Autowired
    @Qualifier("bcryptEncoder")
    private PasswordEncoder passwordEncoder;    
    
    public User createUnverifiedUser(final RegisterForm registerForm) {
        final User user = new User();
        user.setLogin(registerForm.getUsername());
        user.setEmail(registerForm.getEmail());
        user.setHashType(PasswordType.bcrypt);
        user.setDigest(passwordEncoder.encode(registerForm.getPassword()));
        
        final UserStatus userStatus = new UserStatus();
        userStatus.setCreated(OffsetDateTime.now());
        userStatus.setFailedLogins(0);
        userStatus.setLocked(false);
        userStatus.setVerified(false);
        userStatus.setVerificationHash(generateVerificationHash());
        userStatus.setVerificationHashValidUntil(OffsetDateTime.now().plusMinutes(30));
        user.setStatus(userStatus); 
        
        final UserData userData = new UserData();
        user.setUserData(userData);
        
        return user;
    }
    
    public String generateVerificationHash() {
        return new RandomDataGenerator().nextSecureHexString(12);
    }
    
    

}
