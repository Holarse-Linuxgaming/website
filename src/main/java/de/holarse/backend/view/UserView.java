package de.holarse.backend.view;

import de.holarse.backend.db.User;
import java.time.OffsetDateTime;

public class UserView {

    private Integer id;
    private String username;
    private String email;
    private OffsetDateTime lastLogin;

    public static UserView of(final User user) {
        final UserView uv = new UserView();
        uv.id = user.getId();
        uv.username = user.getLogin();
        uv.email = user.getEmail();
        uv.lastLogin = user.getUserStatus() != null ? user.getUserStatus().getLastLogin() : null;
        
        return uv;
    }

    public Integer getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public OffsetDateTime getLastLogin() {
        return lastLogin;
    }
    
}
