package de.holarse.backend.db;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Table(name="roles")
@Entity
public class Role extends Base implements GrantedAuthority {
   
    @Column(unique = true)
    private String code;
    
    @Column(nullable = false)
    private Long clearanceLevel;
    
    @ManyToMany()
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getClearanceLevel() {
        return clearanceLevel;
    }

    public void setClearanceLevel(Long clearanceLevel) {
        this.clearanceLevel = clearanceLevel;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + getCode().toUpperCase();
    }

    @Override
    public String toString() {
        return getAuthority();
    }
    
}
