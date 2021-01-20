package de.holarse.backend.db;

import java.time.OffsetDateTime;
import javax.persistence.*;

@Table(name = "tags")
@Entity
public class Tag extends Base {

    @Column(unique = true)
    private String name;
    
    @Column(name = "name_lang", nullable=false, columnDefinition = "character varying(12) default 'german'")
    private String nameLang;
    
    private Long legacyId;

    private Long weight;
    
    @Column(name = "use_count", columnDefinition = "bigint default 0")
    private Long useCount;
    
    @OneToOne
    private Tag alias;

    @ManyToOne
    private TagGroup tagGroup;


    public Tag() {
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
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


    public TagGroup getTagGroup() {
        return tagGroup;
    }

    public void setTagGroup(TagGroup tagGroup) {
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

    public Long getUseCount() {
        return useCount;
    }

    public void setUseCount(Long useCount) {
        this.useCount = useCount;
    }

    public String getNameLang() {
        return nameLang;
    }

    public void setNameLang(String nameLang) {
        this.nameLang = nameLang;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", legacyId=" + legacyId +
                ", alias=" + alias +
                ", tagGroup=" + tagGroup +
                ", useCount=" + useCount +
                '}';
    }
}
