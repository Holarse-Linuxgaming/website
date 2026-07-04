package de.holarse.backend.db;

import java.time.ZonedDateTime;

import de.holarse.backend.types.ApiRoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Table(name = "apiusers")
@Entity
public class ApiUser extends TimestampedBase {

    private static final long serialVersionUID = 1L;
    
    @Column(unique = true)
    private String login;
    
    private String token;    
    
    @Column(name = "valid_until")    
    private ZonedDateTime validUntil;    
    
    @Enumerated(EnumType.STRING)
    @Column(name = "rolename")
    private ApiRoleType roleName;
    
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

    public ZonedDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(ZonedDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public ApiRoleType getRoleName() {
        return roleName;
    }

    public void setRoleName(ApiRoleType roleName) {
        this.roleName = roleName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
