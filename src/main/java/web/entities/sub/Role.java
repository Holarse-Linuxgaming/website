package web.entities.sub;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Role of a user
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Role implements Serializable  {
    
    @XmlValue
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" + "name=" + name + '}';
    }
    
}
