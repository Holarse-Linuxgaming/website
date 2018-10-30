package de.holarse.backend.db;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "shortnews")
public class ShortNews extends Node implements Frontpagable {

    private String title;
    private String teaser;
    private String teaserImage;

    public String getTeaserImage() {
        return teaserImage;
    }

    public void setTeaserImage(String teaserImage) {
        this.teaserImage = teaserImage;
    }
    
    @Override
    public String getUrl() {
        return "/shortnews/" + getId();
    }
    
    @Override
    public String getUrlid() {
        return getUrl();
    }

    public String getLink() {
        return getContent();
    }

    public void setLink(String link) {
        setContent(link);
    }

    @Override
    public Long getNodeId() {
        return getId();
    }

    @Override
    public String getTeaser() {
        return this.teaser;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.SHORTNEWS;
    }
    
}
