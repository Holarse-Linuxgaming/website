package de.holarse.auth.web;

import de.holarse.backend.db.User;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class HolarsePrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final User user;

    public HolarsePrincipal(final User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER")); // Standardrolle
        roles.addAll(user.getRoles());
        
        return roles;
    }
    
    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getDigest();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getStatus()!= null && !user.getStatus().isLocked();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getStatus() != null && !user.getStatus().isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getStatus() != null && user.getStatus().isVerified();
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonLocked();
    }

    
}
