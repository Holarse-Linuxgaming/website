package de.holarse.backend.db;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;


@Table(name = "roles")
@Entity
public class Role extends TimestampedBase implements Serializable, GrantedAuthority {
    
    private String code;
    private Integer level;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String getAuthority() {
        return String.format("ROLE_%s", getCode().toUpperCase());
    }
    
}
