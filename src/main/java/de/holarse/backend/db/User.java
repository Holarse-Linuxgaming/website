package de.holarse.backend.db;

import de.holarse.backend.types.PasswordType;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

@Table(name = "users")
@Entity
public class User extends TimestampedBase {

    private static final long serialVersionUID = 1L;
    
    private String login;
    private String email;
    private Integer drupalId;
    @Enumerated(EnumType.STRING)
    @Type(PostgreSQLEnumType.class)
    @Column(columnDefinition = "password_type")
    private PasswordType hashType;
    private String digest;
    
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy="user", cascade = CascadeType.PERSIST)
    private UserStatus userStatus;
    
    @OneToOne(mappedBy="user", cascade = CascadeType.PERSIST)
    private UserData userData;    
    
    @OneToMany(mappedBy="user", cascade = CascadeType.PERSIST)
    private Set<UserSlug> userSlugs = new HashSet<>(); 

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserSlug> getUserSlugs() {
        return userSlugs;
    }

    public void setUserSlugs(Set<UserSlug> userSlugs) {
        this.userSlugs = userSlugs;
    }

    public Integer getDrupalId() {
        return drupalId;
    }

    public void setDrupalId(Integer drupalId) {
        this.drupalId = drupalId;
    }

    public PasswordType getHashType() {
        return hashType;
    }

    public void setHashType(PasswordType hashType) {
        this.hashType = hashType;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
    
}
