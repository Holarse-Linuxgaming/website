package de.holarse.web.register;

import de.holarse.backend.db.PasswordType;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    
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
        
        user.setDigest(passwordEncoder.encode(command.getPassword()));
        
        userRepository.saveAndFlush(user);
        return user;        
    }
    
}
