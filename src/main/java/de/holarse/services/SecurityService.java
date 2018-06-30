package de.holarse.services;

import de.holarse.backend.db.Role;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    
    @Autowired
    RoleRepository roleRepository;

    public boolean hasUserHigherClearance(final User user, final Long requiredClearance) {
        if (user == null || requiredClearance == null) {
            return false;
        }
        
        return user.getRoles().stream().anyMatch((role) -> (role.getClearanceLevel() <= requiredClearance));
    }
    
    /**
     * Prüft, ob der eigene Benutzer oder ein Admin versucht zu bearbeiten
     * @param owner
     * @param currentUser
     * @return 
     */
    public boolean hasEditPermissions(final User owner, final User currentUser) {
        if (currentUser == null) {
            return false;
        }
        
        // Eigene Beiträge darf man immer selbst bearbeiten
        if (currentUser.equals(owner)) {
            return true;
        }
        
        final Role adminRole = roleRepository.findByCodeIgnoreCase("ADMIN").orElseThrow(IllegalStateException::new);
        
        // Oder man ist Admin
        return hasUserHigherClearance(currentUser, adminRole.getClearanceLevel());
        
    }
    
}
