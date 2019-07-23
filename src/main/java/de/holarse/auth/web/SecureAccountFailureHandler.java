package de.holarse.auth.web;

import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.repositories.UserStatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecureAccountFailureHandler implements AuthenticationFailureHandler {

    Logger log = LoggerFactory.getLogger(SecureAccountFailureHandler.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatRepository userStatRepository;

    @Autowired
    private UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final AuthenticationException exception) throws IOException, ServletException {


        final String usernameParameter = usernamePasswordAuthenticationFilter.getUsernameParameter();
        final String username = request.getParameter(usernameParameter);

        log.debug("Login für User " + username + " fehlgeschlagen.", exception);
    }
}
