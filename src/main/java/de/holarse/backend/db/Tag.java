package de.holarse.backend.db;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "tags")
@Entity
public class Tag extends Base {

    @Column(unique = true)
    private String name;
    
    private Long legacyId;
    
    @OneToOne
    private Tag alias;
    
    private String tagGroup;

    public Tag() {
    }

    public Tag(String name) {
        super.setCreated(OffsetDateTime.now());
        this.name = name.trim();
    }

    public Tag getAlias() {
        return alias;
    }

    public void setAlias(Tag alias) {
        this.alias = alias;
    }


    public String getTagGroup() {
        return tagGroup;
    }

    public void setTagGroup(String tagGroup) {
        this.tagGroup = tagGroup;
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
