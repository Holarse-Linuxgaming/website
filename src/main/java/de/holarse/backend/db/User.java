package de.holarse.backend.db;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="users")
@Entity
public class User extends Base {
    
    @Column(unique = true)
    private String login;
    
    @Enumerated(EnumType.STRING)
    private PasswordType passwordType;
    
    private String digest;
    
    private String email;
    
    @Column(columnDefinition = "boolean default false")
    private boolean locked;

    @Column(columnDefinition = "boolean default false")
    private boolean verified;

    private String slug;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="users_roles",
        joinColumns = {@JoinColumn(name="users_id", referencedColumnName="id")},
        inverseJoinColumns = {@JoinColumn(name="roles_id", referencedColumnName="id")}
    )    
    private Set<Role> roles;
    
    private Long oldId;
    
    private String verificationKey;
    private OffsetDateTime verificationValidUntil;

    @Column(length = 2048)
    private String signature;
    
    private String avatar;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Message> messages;

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }
    
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    
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
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public PasswordType getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(PasswordType passwordType) {
        this.passwordType = passwordType;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerificationKey() {
        return verificationKey;
    }

    public void setVerificationKey(String verificationKey) {
        this.verificationKey = verificationKey;
    }

    public OffsetDateTime getVerificationValidUntil() {
        return verificationValidUntil;
    }

    public void setVerificationValidUntil(OffsetDateTime verificationValidUntil) {
        this.verificationValidUntil = verificationValidUntil;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "User{" + "login=" + login + ", passwordType=" + passwordType + ", digest=" + digest + ", locked=" + locked + ", roles=" + roles + '}';
    }

}
