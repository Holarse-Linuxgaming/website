package de.holarse.backend.views;

import java.util.List;

/**
 * Enthält eine Liste von Taggroups mit ihren dazugehörigen Tags
 * @author comrad
 */
public class TagGroupView {
    
    private final long id;
    private final String label;
    
    private final List<TagView> tags;
    private final long groupedUseCount;

    public TagGroupView(final long id, final String label, final List<TagView> tags, final long groupedUseCount) {
        this.id = id;
        this.label = label;
        this.tags = tags;
        this.groupedUseCount = groupedUseCount;
    }
    
    public long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public List<TagView> getTags() {
        return tags;
    }

    public long getGroupedUseCount() {
        return groupedUseCount;
    }
    
}
