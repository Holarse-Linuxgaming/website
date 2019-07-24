package de.holarse.auth.web;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserStat;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.repositories.UserStatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.OffsetDateTime;
import java.util.Optional;

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

    @EventListener
    @Transactional
    public void handleLoginSuccessful(InteractiveAuthenticationSuccessEvent evt) {
        HolarsePrincipal loginUser = (HolarsePrincipal) evt.getAuthentication().getPrincipal();
        log.debug("User " + loginUser.getUsername() + " hat sich eingeloggt.");

        // TODO Ist der Benutzer noch auf dem Drupal-Stand, muss er auf BCrypt migriert werden.
        final User user = loginUser.getUser();
        if (user != null) {
            if (!user.getDigest().startsWith("$")) {
                // Migrieren
                user.setDigest(encoder.encode(loginUser.getPassword()));
                userRepository.saveAndFlush(user);
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

            userStatRepository.save(userStat);
        }
    }
}
