package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Table(name="roles")
@Entity
public class Role extends Base implements GrantedAuthority {
   
    @Column(unique = true)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
