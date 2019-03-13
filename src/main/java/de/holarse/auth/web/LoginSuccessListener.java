package de.holarse.auth.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    Logger log = LoggerFactory.getLogger(LoginSuccessListener.class);    
    
    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        HolarsePrincipal user = (HolarsePrincipal) event.getAuthentication().getPrincipal();
        log.debug("User {0} hat sich eingeloggt.", user.getUsername());
    }
    
}
