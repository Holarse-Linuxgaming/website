package de.holarse.auth;

import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class HolarseAuthenticationProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(HolarseAuthenticationProvider.class);
    
    
    @Autowired
    UserRepository ur;
    
    @Autowired
    HolarseAuthentification holarseAuthentication;
    
    @Override
    public Authentication authenticate(final Authentication auth) throws AuthenticationException {
        final String login = auth.getName();
        final String password = auth.getCredentials().toString();
        
        logger.debug("Login: " + login);
        
        final User foundUser = ur.findByLogin(login);
        if (foundUser == null) {
            throw new AuthenticationCredentialsNotFoundException("Login fehlgeschlagen");
        }
        if (foundUser.isLocked()) {
            throw new LockedException("Konto ist gesperrt");
        }
        
        try {
            if (holarseAuthentication.auth(foundUser, login, password)) {
                return new UsernamePasswordAuthenticationToken(login, password, foundUser.getRoles());
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Error während Authentifizierung", ex);
        }
        
        throw new BadCredentialsException("Login fehlgeschlagen");
    }
   
    protected boolean validForLogin(final User user) {
        return !user.isLocked();
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}
