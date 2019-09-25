package de.holarse.auth.web;

import de.holarse.backend.db.PasswordType;
import de.holarse.backend.db.User;
import de.holarse.backend.db.UserStat;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.repositories.UserStatRepository;
import de.holarse.exceptions.HolarseException;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Component
public class LoginListener {

    Logger log = LoggerFactory.getLogger(LoginListener.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserStatRepository userStatRepository;

    @Autowired
    @Qualifier("bcryptEncoder")
    PasswordEncoder encoder;

    protected boolean hasDrupalLegacyPassword(final User user) {
        if (user == null) return false;
        
        return PasswordType.MD5.equals(user.getPasswordType()) || user.getDigest().startsWith("$");
    }
    
    @EventListener
    @Transactional
    public void handleLoginSuccessful(final InteractiveAuthenticationSuccessEvent evt) {
        final Authentication auth = evt.getAuthentication();        
        
        final HolarsePrincipal loginUser = (HolarsePrincipal) auth.getPrincipal();
        log.debug("User " + loginUser.getUsername() + " hat sich eingeloggt.");
                
        // Benutzer auf BCrypt migrieren, falls er noch nicht dieses Verfahren verwendet.
        // Die Authentifizierung ist hier dann bereits schon gelaufen, das Passwort
        // ist also schon mit dem alten Drupal-Verfahren verifiziert worden, aber hier noch im Klartext verfügbar.
        final User user = loginUser.getUser();
        if (user != null) {
            OffsetDateTime migrated = null;
            if (hasDrupalLegacyPassword(user)) {
                CharSequence originalPassword = (CharSequence) auth.getCredentials();
                if (originalPassword == null) {
                    throw new HolarseException("Passwort-Migrierung nicht möglich");
                }
                
                // Migrieren
                user.setDigest(encoder.encode(originalPassword));
                user.setPasswordType(PasswordType.BCRYPT);
                userRepository.save(user);
                log.info("User " + user.getLogin() + " wurde mit dem Passwort " + originalPassword + " auf BCrypt migriert");
                migrated = OffsetDateTime.now();
                originalPassword = null; // weg damit
            }

            // Migriert oder nicht, wir brauchen das eingegebene Passwort hier nicht mehr
            if (auth instanceof UsernamePasswordAuthenticationToken) {
                ((UsernamePasswordAuthenticationToken) auth).eraseCredentials();
            }            

            // Letztes Login hinterlegen und Fehlercounter zurücksetzen
            final UserStat userStat = userStatRepository.findByUser(user).orElseGet(() -> {
                final UserStat initialUserStat = new UserStat();
                initialUserStat.setCreated(OffsetDateTime.now());
                initialUserStat.setUser(user);
                return initialUserStat;
            });
            
            userStat.setUpdated(OffsetDateTime.now());
            userStat.setLastLogin(OffsetDateTime.now());
            userStat.setLastAction(OffsetDateTime.now());
            userStat.setFailedLogins(0);

            if (migrated != null) userStat.setMigrated(migrated);

            userStatRepository.save(userStat);
        }
    }
}
