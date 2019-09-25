package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Table(name="user_stats")
@Entity
public class UserStat extends Base {
    @ManyToOne
    private User user;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime lastLogin;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime lastAction;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime migrated;    

    @Column(columnDefinition = "INT DEFAULT 0")
    private int failedLogins;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getFailedLogins() {
        return failedLogins;
    }

    public void setFailedLogins(int failedLogins) {
        this.failedLogins = failedLogins;
    }

    public OffsetDateTime getLastLogin() {
        return lastLogin;
    }

    public OffsetDateTime getLastAction() {
        return lastAction;
    }

    public void setLastAction(OffsetDateTime lastAction) {
        this.lastAction = lastAction;
    }

    public void setLastLogin(OffsetDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public OffsetDateTime getMigrated() {
        return migrated;
    }

    public void setMigrated(OffsetDateTime migrated) {
        this.migrated = migrated;
    }
    
}
