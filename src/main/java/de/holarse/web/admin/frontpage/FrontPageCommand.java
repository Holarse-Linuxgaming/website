package de.holarse.web.admin.frontpage;

import java.time.OffsetDateTime;

public class FrontPageCommand {
    
    private Long id;
    private String title;
    private String teaser;
    private String url;

    private Long nodeId;
    
    private Boolean pinned;
    
    private OffsetDateTime publishFrom;
    private OffsetDateTime publishUntil;
    private OffsetDateTime cooldownUntil;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getCooldownUntil() {
        return cooldownUntil;
    }

    public void setCooldownUntil(OffsetDateTime cooldownUntil) {
        this.cooldownUntil = cooldownUntil;
    }
    
    
    public Boolean getPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }

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

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    
    
}
