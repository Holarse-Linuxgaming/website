package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;


@Table(name = "roles")
@Entity
public class Role extends TimestampedBase implements GrantedAuthority {

    private static final long serialVersionUID = 1L;
    
    private String code;
    @Column(name="access_level")
    private Integer accessLevel;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public String getAuthority() {
        return String.format("ROLE_%s", getCode().toUpperCase());
    }

    @Override
    public String toString() {
        return String.format("ROLE_%s", getCode().toUpperCase());
    }
    
}
