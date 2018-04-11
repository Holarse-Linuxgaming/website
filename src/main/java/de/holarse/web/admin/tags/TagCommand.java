package de.holarse.web.admin.tags;

import de.holarse.backend.db.Tag;
import de.holarse.backend.db.TagGroup;

public class TagCommand {

    private String name;
    private Tag alias;
    private TagGroup tagGroup;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
