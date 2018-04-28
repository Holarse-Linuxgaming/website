package de.holarse.services;

import de.holarse.backend.db.User;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public boolean hasUserHigherClearance(final User user, final Long requiredClearance) {
        if (user == null || requiredClearance == null) {
            return false;
        }
        
        return user.getRoles().stream().anyMatch((role) -> (role.getClearanceLevel() <= requiredClearance));
    }
    
}
