package de.holarse.backend.views;

import de.holarse.backend.db.types.AttachmentGroup;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class NewsView extends AbstractLinkView implements ContentView, PageTitleView, SlugView {

    private Long nodeId;
    private String title;
    private String subtitle;
    
    private String content;
    private String formattedContent;
    private String plainContent;
    private String teaser;
    
    private String created;
    private String updated;
    private String slug;
    
    private final Map<AttachmentGroup, List<AttachmentView>> attachments = new HashMap<>();    
    
//    @Override
//    public String getUrl() {
//        return String.format("/news/%s", slug);
//    }
//    
//    @Override
//    public String getEditUrl() {
//        return String.format("/news/%s/edit", nodeId);
//    }    

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

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getFormattedContent() {
        return formattedContent;
    }

    @Override
    public void setFormattedContent(String formattedContent) {
        this.formattedContent = formattedContent;
    }

    @Override
    public String getPlainContent() {
        return plainContent;
    }

    @Override
    public void setPlainContent(String plainContent) {
        this.plainContent = plainContent;
    }

    @Override
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
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
