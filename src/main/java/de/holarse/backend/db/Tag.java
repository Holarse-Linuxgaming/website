package de.holarse.backend.db;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tags")
@Entity
public class Tag extends Base {

    @Column(unique = true)
    private String name;
    
    private Long legacyId;

    public Tag() {
    }

    public Tag(String name) {
        super.setCreated(OffsetDateTime.now());
        this.name = name.trim();
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(Long legacyId) {
        this.legacyId = legacyId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
}
