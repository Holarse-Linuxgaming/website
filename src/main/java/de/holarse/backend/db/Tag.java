package de.holarse.backend.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag extends TimestampedBase {

    public Tag() {
    }

    public Tag(final String name) {
        this.name = name;
    }
    
    @Column(length = 255)
    private String name;
    
    @Column(length = 12, name = "name_lang")
    private String nameLang;

    @Column
    private int weight;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="aliasid", nullable=true, referencedColumnName = "id")
    private Tag alias;
    
    @ManyToOne
    @JoinColumn(name="taggroupid", nullable=false, referencedColumnName = "id")
    private TagGroup tagGroup;
    
    @Column
    private String slug;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="update_userid", nullable=true, referencedColumnName = "id")
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLang() {
        return nameLang;
    }

    public void setNameLang(String nameLang) {
        this.nameLang = nameLang;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
    
}
