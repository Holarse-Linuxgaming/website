package de.holarse.backend.api;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JacksonXmlRootElement(localName = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JacksonXmlProperty(localName = "uid", isAttribute = true)
    private Long uid;
    
    @JacksonXmlProperty(localName="created")    
    private Date created;
    
    @JacksonXmlProperty(localName="login")    
    private String login;
    
    @JacksonXmlProperty(localName="signature")    
    @JacksonXmlCData     
    private String signature;
    
    @JacksonXmlProperty(localName="email")    
    private String email;
    
    @JacksonXmlProperty(localName="locked", isAttribute = true)    
    private boolean locked;
    
    @JacksonXmlProperty(localName="avatar")    
    private String avatar;

    @JacksonXmlProperty(localName="password")    
    private Password password;
    
    @JacksonXmlProperty(localName="role")
    @JacksonXmlElementWrapper(localName = "roles")
    private List<Role> roles = new ArrayList<>();    

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
