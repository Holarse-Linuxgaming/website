package de.holarse.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class HolarseApiAuthenticationProvider implements AuthenticationProvider {
    
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = (String) authentication.getPrincipal();
        final String password = (String) authentication.getCredentials();
        
        if (username.equals("admin") && password.equals("admin")) {
            final List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("API"));
            
            return new UsernamePasswordAuthenticationToken(username, password, roles);
        }
        
        return null;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
    
}
