package de.holarse.backend.db;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "videonews")
public class VideoNews extends Base implements Frontpagable {

    private String title;    
    private String teaser;
    private String teaserImage;
    private String link;

    @Override
    public String getTeaserImage() {
        return teaserImage;
    }

    public void setTeaserImage(String teaserImage) {
        this.teaserImage = teaserImage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    @Override
    public Long getNodeId() {
        return getId();
    }

    @Override
    public String getTeaser() {
        return teaser;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUrl() {
        return "/videonews/" + getId();
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.VIDEO;
    }
    
}
