package de.holarse.backend.db;

import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "user_status")
@Entity
public class UserStatus extends TimestampedBase {

    private static final long serialVersionUID = 1L;
    
    private boolean locked;
    private boolean verified;
    
    @Column(insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP", name = "last_login")    
    private OffsetDateTime lastLogin;      

    @Column(insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP", name = "last_action")    
    private OffsetDateTime lastAction;

    @Column(insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP", name = "migrated")    
    private OffsetDateTime migrated;    
    
    @Column(name="failed_logins")
    private int failedLogins;
    
    @Column(name = "verification_hash")
    private String verificationHash;
    
    @Column(name = "verification_hash_validuntil")
    private OffsetDateTime verificationHashValidUntil;
    
    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

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

    public OffsetDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(OffsetDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public OffsetDateTime getLastAction() {
        return lastAction;
    }

    public void setLastAction(OffsetDateTime lastAction) {
        this.lastAction = lastAction;
    }

    public OffsetDateTime getMigrated() {
        return migrated;
    }

    public void setMigrated(OffsetDateTime migrated) {
        this.migrated = migrated;
    }

    public int getFailedLogins() {
        return failedLogins;
    }

    public void setFailedLogins(int failedLogins) {
        this.failedLogins = failedLogins;
    }

    public String getVerificationHash() {
        return verificationHash;
    }

    public void setVerificationHash(String verificationHash) {
        this.verificationHash = verificationHash;
    }

    public OffsetDateTime getVerificationHashValidUntil() {
        return verificationHashValidUntil;
    }

    public void setVerificationHashValidUntil(OffsetDateTime verificationHashValidUntil) {
        this.verificationHashValidUntil = verificationHashValidUntil;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
