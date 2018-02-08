package de.holarse.auth;

import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class HolarseAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepository ur;
    
    @Autowired
    LegacyDrupalAuthentification holarseAuthentication;
    
    @Override
    public Authentication authenticate(final Authentication auth) throws AuthenticationException {
        final String login = auth.getName();
        final String password = auth.getCredentials().toString();
        
        final User foundUser = ur.findByLogin(login);
        if (foundUser != null && validForLogin(foundUser) && holarseAuthentication.auth(foundUser, login, password)) { 
            return new UsernamePasswordAuthenticationToken(login, password, foundUser.getRoles());
        }
        
        throw new AuthenticationCredentialsNotFoundException("Login failed for " + login);
    }
    
    protected boolean validForLogin(final User user) {
        return !user.isLocked();
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}
