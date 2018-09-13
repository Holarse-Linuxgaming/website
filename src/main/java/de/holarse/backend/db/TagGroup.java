package de.holarse.backend.db;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Table(name = "taggroups")
@Entity
public class TagGroup extends Base {

    private String name;

    private Long weight;

    @OneToMany(mappedBy = "tagGroup", fetch = FetchType.LAZY)
    private Set<Tag> tags;

    public Set<Tag> getTags() {
        return tags;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
