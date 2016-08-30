package de.holarse.website.models;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * News
 * @author britter
 */
@Entity(name = "news")
public class News extends MultimediaNode {

    @Column(length = 1024)    
    private String alternativeTitle;
    @Temporal(TemporalType.TIMESTAMP)
    private Date postedAt;
    @ManyToOne
    private Category category;
    
    @OneToMany(mappedBy = "news")
    private Set<NewsUpdate> updates;

    public Set<NewsUpdate> getUpdates() {
        return updates;
    }

    public void setUpdates(Set<NewsUpdate> updates) {
        this.updates = updates;
    }

    public String getAlternativeTitle() {
        return alternativeTitle;
    }

    public void setAlternativeTitle(String alternativeTitle) {
        this.alternativeTitle = alternativeTitle;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
}
