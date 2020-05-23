package de.holarse.services;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.Role;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.RoleRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    
    @Autowired
    RoleRepository roleRepository;

    public boolean hasClearance(final Authentication authentication, final String roleCode) {
        return hasClearance(authentication, getRoleByName(roleCode).orElseThrow(IllegalStateException::new));
    }

    /**
     * Prüft, die Berechtigung anhand eines Spring Security-Authenticationtokens.
     * @param authentication
     * @param requiredRole
     * @return
     */
    public boolean hasClearance(final Authentication authentication, final Role requiredRole) {
        // Ein Gast kann niemals eine Berechtigungsrolle haben
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        final HolarsePrincipal hp = (HolarsePrincipal) authentication.getPrincipal();
        final User user = hp.getUser();

        return hasClearance(user, requiredRole);
    }

    /**
     * Prüft, ob der Benutzer die geforderte Rolle hat, oder ob eine
     * höhere Rolle vorliegt.
     * @param user
     * @param requiredRole
     * @return
     */
    public boolean hasClearance(final User user, final Role requiredRole) {
        if (user == null || requiredRole == null)
            return false;

        if (user.isLocked() && !user.isVerified())
            return false;

        return user.getRoles().stream().anyMatch(role -> role.getClearanceLevel() >= requiredRole.getClearanceLevel());
    }

    public Optional<Role> getRoleByName(final String code) {
        return roleRepository.findByCodeIgnoreCase(code);
    }
    
    /**
     * Prüft, ob der eigene Benutzer oder ein Moderator versucht zu bearbeiten
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

        // Der aktuelle Benutzer hat mindestens Moderatoren-Rechte
        return hasClearance(currentUser, getRoleByName("MODERATOR").orElseThrow(IllegalStateException::new));
    }
    
}
