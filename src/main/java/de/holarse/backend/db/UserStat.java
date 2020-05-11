package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Table(name="users_stats")
@Entity
public class UserStat extends Base {
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime lastLogin;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime lastAction;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime migrated;    

    @Column(columnDefinition = "INT DEFAULT 0")
    private int failedLogins;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
