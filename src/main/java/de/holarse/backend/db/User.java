package de.holarse.backend.db;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity(name = "users")
public class User extends Base {
    
    @Column(unique = true)
    private String login;
    
    @Enumerated(EnumType.STRING)
    private PasswordType passwordType;
    
    private String digest;
    
    @Column(columnDefinition = "boolean default false")
    private boolean locked;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public PasswordType getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(PasswordType passwordType) {
        this.passwordType = passwordType;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "User{" + "login=" + login + ", passwordType=" + passwordType + ", digest=" + digest + ", locked=" + locked + ", roles=" + roles + '}';
    }
    
    public boolean isGuest() { return false; }
    
}
