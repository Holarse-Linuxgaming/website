package de.holarse.website.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "links")
public class Link extends EntityBase {
    
    @ManyToOne
    private Linkgroup group;
    
    @Column(length = 2048)
    private String url;

    public Linkgroup getGroup() {
        return group;
    }

    public void setGroup(Linkgroup group) {
        this.group = group;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
