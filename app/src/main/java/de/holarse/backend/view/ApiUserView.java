package de.holarse.backend.view;

import de.holarse.backend.db.ApiUser;
import de.holarse.backend.types.ApiRoleType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class ApiUserView {

    private Integer id;
    
    @NotNull
    private String login;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDate validUntil;
    
    @NotNull
    private ApiRoleType roleName;
    
    private boolean active;
    
    private OffsetDateTime created;
    private OffsetDateTime updated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    public OffsetDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(OffsetDateTime updated) {
        this.updated = updated;
    }

    public ApiRoleType getRoleName() {
        return roleName;
    }

    public void setRoleName(ApiRoleType roleName) {
        this.roleName = roleName;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }


}
