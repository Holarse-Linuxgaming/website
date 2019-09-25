package de.holarse.web.register;

import de.holarse.backend.db.PasswordType;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import java.time.OffsetDateTime;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    
    Logger log = LoggerFactory.getLogger(RegisterService.class);
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    @Qualifier("bcryptEncoder")
    PasswordEncoder passwordEncoder;
    
    public User createAccount(final RegisterCommand command) {
        final User user = new User();
        user.setLogin(command.getLogin());
        user.setPasswordType(PasswordType.BCRYPT);
        user.setEmail(command.getEmail());
        user.setCreated(OffsetDateTime.now());

        // Verifizierung auf 12h setzen
        user.setVerified(false);
        user.setVerificationKey(createVerificationKey());
        user.setVerificationValidUntil(getVerificationUntilDate(OffsetDateTime.now()));        
        
        user.setDigest(passwordEncoder.encode(command.getPassword()));
        
        userRepository.save(user);
        return user;        
    }
    
    /**
     * Verifiziert einen User anhand seines Verifizierungskeys
     * @param verificationKey
     * @return 
     */
    public String verifyAccount(final String verificationKey) {
        log.debug("Verifying for {}", new Object[] { verificationKey });
        
        final User user = userRepository.findByVerificationKey(verificationKey);
        if (user == null) {
            return "verification-failed";
        }
        
        if (OffsetDateTime.now().isAfter(user.getVerificationValidUntil())) {                
            return "verification-expired";
        }
        
        user.setVerified(true);
        user.setUpdated(OffsetDateTime.now());
        user.setVerificationKey(null);
        userRepository.save(user);
        
        return "verified";
    }    
    
    /**
     * Fügt 12h zum übergebenen Datum hinzu
     * @param cal
     * @return 
     */
    protected OffsetDateTime getVerificationUntilDate(final OffsetDateTime cal) {
        return cal.plusHours(12);
    }
    
    /**
     * Erzeugt einen gehashten Key
     * @return 
     */
    protected String createVerificationKey() {
        return new RandomDataGenerator().nextSecureHexString(12);
    }
    
}
