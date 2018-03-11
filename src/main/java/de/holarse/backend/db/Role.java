package de.holarse.backend.db;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Table(name="users")
@Entity
public class Role extends Base implements GrantedAuthority {
   
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getCode();
    }

    @Override
    public String toString() {
        return "Role{" + "code=" + code + ", name=" + name + '}';
    }
    
}
