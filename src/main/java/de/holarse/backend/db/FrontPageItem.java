package de.holarse.backend.db;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "fpitems")
@Entity
public class FrontPageItem extends Base {
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")    
    private OffsetDateTime publishFrom;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")    
    private OffsetDateTime publishUntil;    
    
    private String title;
    private String teaser;
    
    // Image
    
    private String url;
    
    private boolean pinned;
    
    private Long nodeId;

    public OffsetDateTime getPublishFrom() {
        return publishFrom;
    }

    public void setPublishFrom(OffsetDateTime publishFrom) {
        this.publishFrom = publishFrom;
    }

    public OffsetDateTime getPublishUntil() {
        return publishUntil;
    }

    public void setPublishUntil(OffsetDateTime publishUntil) {
        this.publishUntil = publishUntil;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }
    
}
