package de.holarse.auth.web;

import de.holarse.auth.api.HolarseApiUserDetailsService;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("webUserDetailsService")
public class HolarseWebUserDetailsService implements UserDetailsService {

    Logger log = LoggerFactory.getLogger(HolarseWebUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        log.debug("LOADED WEB USER " + user);        
        
        return new HolarsePrincipal(user);
    }

}
