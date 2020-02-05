package de.holarse.backend.views;

import java.util.List;

/**
 * Enthält eine Liste von Taggroups mit ihren dazugehörigen Tags
 * @author comrad
 */
public class TagGroupView {
    
    private final String label;
    
    private final List<TagView> tags;
    private final long groupedUseCount;

    public TagGroupView(final String label, final List<TagView> tags, final long groupedUseCount) {
        this.label = label;
        this.tags = tags;
        this.groupedUseCount = groupedUseCount;
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
