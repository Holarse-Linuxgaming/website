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
    
    @Column(nullable = false)
    private Long clearanceLevel;
    
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

    /**
     * Gibt die Rolle als Spring Security-Authority zurück
     */
    @Override
    public String getAuthority() {
        return "ROLE_" + getCode().toUpperCase();
    }

    @Override
    public String toString() {
        return getAuthority();
    }
    
}
