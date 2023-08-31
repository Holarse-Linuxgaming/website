package de.holarse.backend.db;

import java.time.OffsetDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table
@Entity(name = "apiusers")
public class ApiUser extends TimestampedBase {

    private static final long serialVersionUID = 1L;
    
    @Column(unique = true)
    private String login;
    
    private String token;    
    
    @Column(name = "valid_until")    
    private OffsetDateTime validUntil;    
    
    private String roleName;
    
    @Column(columnDefinition = "boolean default true")
    private boolean active;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OffsetDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(OffsetDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
