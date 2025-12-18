package de.holarse.auth.web;

import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import jakarta.transaction.Transactional;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("webUserDetailsService")
public class HolarseWebUserDetailsService implements UserDetailsService {

    private final static transient Logger log = LoggerFactory.getLogger(HolarseWebUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        Hibernate.initialize(user.getRoles());
        
        return new HolarsePrincipal(user);
    }

}
