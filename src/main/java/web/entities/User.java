package web.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import web.entities.sub.Role;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Entity {
    
    @XmlAttribute
    private Long uid;
    
    @XmlElement    
    private String login;
    @XmlElement    
    private String signature;
    
    @XmlElementWrapper(name = "roles")    
    @XmlElement(name = "role")
    private List<Role> roles = new ArrayList<>();    

    public User() {
    }

    @Override
    public Long getUid() {
        return uid;
    }

    @Override
    public void setUid(Long uid) {
        this.uid = uid;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" + "uid=" + uid + ", login=" + login + ", signature=" + signature + ", roles=" + roles + '}';
    }
    
    
    
}
