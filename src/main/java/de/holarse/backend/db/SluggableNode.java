package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * TODO Diese Entity kann irgendwann raus. Eigentlich wäre es sinnvoller direkt die Slugtabelle zu benutzen, anstatt
 * den aktuellen Slug noch mal in der jeweiligen Node zu halten. Index dann weiterhin Nodetyp-übergreifend
 * über die mv_slugs.
 */
@MappedSuperclass
public abstract class SluggableNode extends CommentableNode implements Sluggable {
    
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
