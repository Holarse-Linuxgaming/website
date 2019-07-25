package de.holarse.auth.web;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserStat;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.repositories.UserStatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;

/**
 * Der SecureAccountFailureHandler merkt sich bei einem bekannten Benutzerkonto die
 * Anzahl der Fehlversuche und erhöhte diese.
 */
@Component
public class SecureAccountFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    Logger log = LoggerFactory.getLogger(SecureAccountFailureHandler.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatRepository userStatRepository;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {


        final String username = request.getParameter("login");

        log.debug("Login für User " + username + " fehlgeschlagen.", exception);

        final User user = userRepository.findByLogin(username);
        if (user != null) {
            final UserStat stats = userStatRepository.findByUser(user).orElseGet(userStatRepository.createDefaultUserStat());
            stats.setUser(user);
            stats.setFailedLogins(stats.getFailedLogins() + 1);
            stats.setUpdated(OffsetDateTime.now());

            userStatRepository.save(stats);

            if (!user.isLocked() && stats.getFailedLogins() > 3) {
                user.setLocked(true);
                user.setUpdated(OffsetDateTime.now());
                userRepository.save(user);
                log.info("Benutzer " + user.getLogin() + " wurde wegen zu vielen Fehlversuchen gesperrt.");
            }
        }                                 
        
        super.setDefaultFailureUrl("/login?error=true");
        super.onAuthenticationFailure(request, response, exception);
    }
}
