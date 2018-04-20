package de.holarse.backend.db;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Table(name = "taggroups")
@Entity
public class TagGroup extends Base {

    @Enumerated(EnumType.STRING)
    private TagGroupType name;

    @OneToMany(mappedBy = "tagGroup")
    private Set<Tag> tags;

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public TagGroupType getName() {

        return name;
    }

    public void setName(TagGroupType name) {
        this.name = name;
    }
}
