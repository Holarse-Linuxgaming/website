package de.holarse.auth.api;

import de.holarse.backend.db.ApiUser;
import de.holarse.backend.db.repositories.ApiUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("apiUserDetailsService")
public class HolarseApiUserDetailsService implements UserDetailsService {

    private final static transient Logger log = LoggerFactory.getLogger(HolarseApiUserDetailsService.class);    
    
    @Autowired
    private ApiUserRepository apiUserRepository;
    
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final ApiUser user = apiUserRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        log.debug("LOADED API USER " + user);
        
        return new ApiPrincipal(user);
    }
    
}
