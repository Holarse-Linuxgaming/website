package de.holarse.web.admin.frontpage;

import de.holarse.backend.db.Frontpagable;
import de.holarse.backend.db.NodeType;
import java.time.OffsetDateTime;

public class FrontPageCommand {
    
    private Long id;
    private String title;
    private String teaser;
    private String url;

    private Long nodeId;
    private NodeType nodeType;
    
    private Boolean pinned;
    
    private OffsetDateTime publishFrom;
    private OffsetDateTime publishUntil;
    private OffsetDateTime cooldownUntil;
    
    private boolean repostable;

    public FrontPageCommand() {
    }

    public FrontPageCommand(final Frontpagable frontpagable) {
        setNodeId(frontpagable.getNodeId());
        setTitle(frontpagable.getTitle());        
        setTeaser(frontpagable.getTeaser());
        setUrl(frontpagable.getUrl());
    }
    
    public String getUrl() {
        return url;
    }

    public final void setUrl(String url) {
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

    public final void setTitle(String title) {
        this.title = title;
    }

    public String getTeaser() {
        return teaser;
    }

    public final void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public final void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public boolean isRepostable() {
        return repostable;
    }

    public void setRepostable(boolean repostable) {
        this.repostable = repostable;
    }  
    
    
}
