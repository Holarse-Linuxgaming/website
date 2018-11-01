package de.holarse.backend.db;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "frontpage")
@Entity
public class FrontPageItem extends Base implements Frontpagable {
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")    
    private OffsetDateTime publishFrom;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")    
    private OffsetDateTime publishUntil;    
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")    
    private OffsetDateTime cooldownUntil;       
    
    private String title;
    private String teaser;
    
    // Image
    private String teaserImage;
    
    private String url;
    
    private boolean pinned;
    
    private Long nodeId;
    
    @Enumerated(EnumType.STRING)
    private NodeType nodeType;
    
    @Transient
    private boolean repostable;

    @PostLoad
    private void fpiPostLoad() {
        repostable = getCooldownUntil() != null && OffsetDateTime.now().isAfter(getCooldownUntil());
    }    
    
    public OffsetDateTime getCooldownUntil() {
        return cooldownUntil;
    }

    public void setCooldownUntil(OffsetDateTime cooldownUntil) {
        this.cooldownUntil = cooldownUntil;
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

    public boolean isRepostable() {
        return repostable;
    }

    public void setRepostable(boolean repostable) {
        this.repostable = repostable;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    @Override
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

    @Override
    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getTeaserImage() {
        return teaserImage;
    }

    public void setTeaserImage(String teaserImage) {
        this.teaserImage = teaserImage;
    }
    
}
