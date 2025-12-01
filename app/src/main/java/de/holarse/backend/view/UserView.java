package de.holarse.backend.view;

import de.holarse.backend.db.User;
import java.time.OffsetDateTime;

public class UserView {

    private Integer id;
    private String login;
    private String email;
    private boolean locked;
    private boolean verified;
    private int failedLogins;
    private OffsetDateTime lastLogin;

    public static UserView of(final User user) {
        final UserView uv = new UserView();
        uv.id = user.getId();
        uv.login = user.getLogin();
        uv.email = user.getEmail();
        if (user.getStatus() != null) {
            uv.locked = user.getStatus().isLocked();
            uv.verified = user.getStatus().isVerified();
            uv.failedLogins = user.getStatus().getFailedLogins();
            uv.lastLogin = user.getStatus().getLastLogin();
        }
        
        return uv;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastLogin(OffsetDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getEmail() {
        return email;
    }

    public OffsetDateTime getLastLogin() {
        return lastLogin;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getFailedLogins() {
        return failedLogins;
    }

    public void setFailedLogins(int failedLogins) {
        this.failedLogins = failedLogins;
    }

    @Override
    public String toString() {
        return "UserView{" + "id=" + id + ", login=" + login + ", email=" + email + ", locked=" + locked + ", verified=" + verified + ", failedLogins=" + failedLogins + ", lastLogin=" + lastLogin + '}';
    }


}
