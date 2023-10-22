package de.holarse.backend.view;

import de.holarse.backend.db.ApiUser;
import java.time.OffsetDateTime;

public class ApiUserView {

    private Integer id;
    private String login;
    private String token;
    private OffsetDateTime validUntil;
    private String roleName;
    private boolean active;
    private OffsetDateTime created;
    private OffsetDateTime updated;

    public static ApiUserView of(final ApiUser apiuser) {
        final ApiUserView uv = new ApiUserView();
        uv.setId(apiuser.getId());
        uv.setLogin(apiuser.getLogin());
        uv.setToken(apiuser.getToken());
        uv.setValidUntil(apiuser.getValidUntil());
        uv.setRoleName(apiuser.getRoleName());
        uv.setCreated(apiuser.getCreated());
        uv.setUpdated(apiuser.getUpdated());
        uv.setActive(apiuser.isActive());

        return uv;
    }

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


}
