package de.holarse.backend.views;

import de.holarse.backend.db.types.AttachmentGroup;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class NewsView implements ContentView {

    private Long nodeId;
    private String title;
    private String subtitle;
    
    private String content;
    private String formattedContent;
    private String plainContent;
    private String teaser;
    
    private OffsetDateTime created;
    private OffsetDateTime updated;
    private String slug;
    
    private final Map<AttachmentGroup, List<AttachmentView>> attachments = new HashMap<>();    
    
    @Override
    public String getUrl() {
        return String.format("/news/%s", slug);
    }
    
    @Override
    public String getEditUrl() {
        return String.format("/news/%s/edit", nodeId);
    }    

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getFormattedContent() {
        return formattedContent;
    }

    public void setFormattedContent(String formattedContent) {
        this.formattedContent = formattedContent;
    }

    @Override
    public String getPlainContent() {
        return plainContent;
    }

    public void setPlainContent(String plainContent) {
        this.plainContent = plainContent;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    public OffsetDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(OffsetDateTime updated) {
        this.updated = updated;
    }

    /**
     * Noch unklar, wie wir das benutzen wollen. Eigentlich
     * müssten wir das Teaserergebnis
     * @param teaser
     */    
    @Deprecated
    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    @Override
    public String getTeaser() {
        return StringUtils.abbreviate(content, 200);
    }    

    @Override
    public String getPageTitle() {
        return String.format("%s", title);
    }

    public Map<AttachmentGroup, List<AttachmentView>> getAttachments() {
        return attachments;
    }
    
    public List<AttachmentView> getVideos() {
        return getAttachments().get(AttachmentGroup.VIDEO);
    }
    
    public List<AttachmentView> getScreenshots() {
        return getAttachments().get(AttachmentGroup.IMAGE);
    }    
    
    public List<AttachmentView> getWebsites() {
        return getAttachments().get(AttachmentGroup.WEBSITE);
    }    
    
}
