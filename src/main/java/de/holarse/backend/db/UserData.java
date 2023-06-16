package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "user_data")
@Entity
public class UserData extends TimestampedBase {

    private static final long serialVersionUID = 1L;
    
    @Column(length = 1024)
    private String signature;

    private String avatar;
    
    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;    

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
