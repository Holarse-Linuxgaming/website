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

    private final static String ERROR_STRING = "Benutzername oder Passwort ist falsch.";

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

        super.setDefaultFailureUrl("/login?error=1");

        final String username = request.getParameter("username");
        final String errorMsg = handleUserState(username);

        log.debug("Login für User {} fehlgeschlagen.", username, exception);

        request.getSession().setAttribute("loginError", errorMsg);

        super.onAuthenticationFailure(request, response, exception);
    }

    private String handleUserState(final String username) {
        final User user = userRepository.findByLogin(username);
        if (user == null) {
            log.warn("User {} was not found in database", username);
            return ERROR_STRING;
        }

        final UserStatus userStatus = user.getStatus();
        if (userStatus == null) {
            log.warn("User {} has no userstatus", user);
            return ERROR_STRING;
        }

        if (userStatus.isLocked()) {
            log.warn("User {} tried to login on locked account", user);
            return ERROR_STRING;
        }

        userStatus.setFailedLogins(user.getStatus().getFailedLogins() + 1);
        userStatus.setUpdated(OffsetDateTime.now());
        
        if (userStatus.getFailedLogins() > MAX_FAILED_LOGINS) {
            log.warn("User {} exceeded max login attempts", user);
            userStatus.setLocked(true);
            userStatusRepository.save(userStatus);
            return ERROR_STRING;
        }
        
        userStatusRepository.save(userStatus);

        return ERROR_STRING;
    }

}
