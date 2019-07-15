package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class SluggableNode extends CommentableNode implements Sluggable, LinkableNode {
    
    @Column(unique = true, nullable = false)
    private String slug;

    @Override
    public String getSlug() {
        return slug;
    }

    @Override
    public void setSlug(String slug) {
        this.slug = slug;
    }
   
}
