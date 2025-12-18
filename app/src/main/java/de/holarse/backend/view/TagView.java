package de.holarse.backend.view;

import de.holarse.backend.db.Tag;

public class TagView {
    
    private String name;
    private String slug;
    private Integer useCount;
    private Integer weight;
    
    public static TagView of(final Tag tag) {
        final TagView view = new TagView();
        view.setName(tag.getName());
        view.setSlug(tag.getSlug());
        view.setUseCount(tag.getUseCount());
        view.setWeight(tag.getWeight());

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

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
