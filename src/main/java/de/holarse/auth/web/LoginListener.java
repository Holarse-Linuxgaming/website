package de.holarse.auth.web;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserStatus;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.repositories.UserStatusRepository;
import de.holarse.backend.types.PasswordType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import org.springframework.security.core.Authentication;

@Component
public class LoginListener {

    private final static transient Logger log = LoggerFactory.getLogger(LoginListener.class);

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserStatusRepository userStatusRepository;

    @Autowired
    @Qualifier("bcryptEncoder")
    PasswordEncoder encoder;

    protected boolean hasDrupalLegacyPassword(final User user) {
        if (user == null) {
            return false;
        }
        
        return PasswordType.md5.equals(user.getHashType()) || !user.getDigest().startsWith("$");
    }
    
    @EventListener
    @Transactional
    public void handleLoginSuccessful(final InteractiveAuthenticationSuccessEvent evt) {
        final Authentication auth = evt.getAuthentication();        
        
        final HolarsePrincipal loginUser = (HolarsePrincipal) auth.getPrincipal();
        log.trace("User {} hat sich erfolgreich eingeloggt.", loginUser.getUsername());
                
        // Benutzer auf BCrypt migrieren, falls er noch nicht dieses Verfahren verwendet.
        // Die Authentifizierung ist hier dann bereits schon gelaufen, das Passwort
        // ist also schon mit dem alten Drupal-Verfahren verifiziert worden, aber hier noch im Klartext verfügbar.
        final User user = loginUser.getUser();
        
        OffsetDateTime migrated = null;
        if (hasDrupalLegacyPassword(user)) {
            log.warn("Passwort von Benutzer {} muss migriert werden.", user.getLogin());

            CharSequence originalPassword = (CharSequence) auth.getCredentials();
            if (originalPassword == null) {
                throw new IllegalStateException("Passwort-Migrierung nicht möglich");
            }

            // Migrieren
            user.setDigest(encoder.encode(originalPassword));
            user.setHashType(PasswordType.bcrypt);
            log.info("User " + user.getLogin() + " wurde mit dem Passwort " + originalPassword + " auf BCrypt migriert");
            migrated = OffsetDateTime.now();
            originalPassword = null; // weg damit
        }

        // Migriert oder nicht, wir brauchen das eingegebene Passwort hier nicht mehr
//            if (auth instanceof UsernamePasswordAuthenticationToken) {
//                ((UsernamePasswordAuthenticationToken) auth).eraseCredentials();
//            }            

        // Letztes Login hinterlegen und Fehlercounter zurücksetzen
        user.getStatus().setUpdated(OffsetDateTime.now());
        user.getStatus().setLastLogin(OffsetDateTime.now());
        user.getStatus().setLastAction(OffsetDateTime.now());
        user.getStatus().setFailedLogins(0); // Login-Fehlversuche wieder zurücksetzen

        if (migrated != null) { user.getStatus().setMigrated(migrated); }
        userRepository.save(user);
    }
}
