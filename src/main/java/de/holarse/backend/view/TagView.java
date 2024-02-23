package de.holarse.backend.view;

import de.holarse.backend.db.Tag;

public class TagView {
    
    private String name;
    private String slug;
    private int useCount;
    
    public static TagView of(final Tag tag) {
        final TagView view = new TagView();
        view.setName(tag.getName());
        view.setSlug(tag.getSlug());
        view.setUseCount(tag.getUseCount());

        return view;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }
}
