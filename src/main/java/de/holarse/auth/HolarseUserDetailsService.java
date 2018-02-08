package de.holarse.auth;

import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HolarseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        return new HolarsePrincipal(user);
    }
    
}
