package de.holarse.auth.api;

import de.holarse.backend.db.ApiUser;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author comrad
 */
public class ApiPrincipal implements UserDetails {

    private final static transient Logger log = LoggerFactory.getLogger(ApiPrincipal.class);  
    
    private final ApiUser user;

    public ApiPrincipal(final ApiUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_API"));
        roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRoleName().toUpperCase()));

        log.debug("roles: " + roles);
        
        return roles;
    }
    
    public ApiUser getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        log.debug("password requ: " + user.getToken());
        return user.getToken();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getValidUntil() == null || user.getValidUntil().isBefore(OffsetDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getValidUntil() == null || user.getValidUntil().isBefore(OffsetDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    
}
