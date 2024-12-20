package de.holarse.auth.web;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserStatus;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.repositories.UserStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;

/**
 * Der SecureAccountFailureHandler merkt sich bei einem bekannten Benutzerkonto die
 * Anzahl der Fehlversuche und erhöhte diese.
 */
@Component
public class SecureAccountFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final static transient Logger log = LoggerFactory.getLogger(SecureAccountFailureHandler.class);
    
    private final static transient int MAX_FAILED_LOGINS = 3;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserStatusRepository userStatusRepository;

//    @Autowired
//    private UserStatRepository userStatRepository;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {


        final String username = request.getParameter("username");
        log.debug("Login für User {} fehlgeschlagen.", username, exception);

        final User user = userRepository.findByLogin(username);
        if (user != null) {
            final UserStatus userStatus = user.getStatus();
            if (userStatus != null) {
                userStatus.setFailedLogins(user.getStatus().getFailedLogins() + 1);
                userStatus.setUpdated(OffsetDateTime.now());

                log.info("Benutzer {} hat nun {} fehlgeschlagene Login-Versuche.", username, user.getStatus().getFailedLogins());

                if (!userStatus.isLocked() && hasTooManyFailedAttempts(userStatus)) {
                    userStatus.setLocked(true);
                    log.warn("Benutzer {} wurde wegen zu vielen Fehlversuchen gesperrt.", username);
                    userStatusRepository.save(userStatus);
                    super.setDefaultFailureUrl("/login?error=too-many-attempts");
                } else {
                    log.warn("Benutzer {} ist gesperrt.", username);
                    super.setDefaultFailureUrl("/login?error=locked");
                }


            } else {
                log.error("User login {} has no user_status assoc", username);
                super.setDefaultFailureUrl("/login?error=incomplete");
            }
        } else {
            log.error("User login {} is not known", username);
            super.setDefaultFailureUrl("/login?error=invalid");
        }

        super.onAuthenticationFailure(request, response, exception);
    }
    
    private boolean hasTooManyFailedAttempts(final UserStatus userStatus) {
        if (userStatus == null) {
            return true;
        }
        
        return userStatus.getFailedLogins() > MAX_FAILED_LOGINS;
    }
}
