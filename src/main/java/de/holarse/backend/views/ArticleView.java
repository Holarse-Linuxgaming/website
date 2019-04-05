package de.holarse.backend.views;

import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.Comment;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.types.AttachmentGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Viewmodel für Artikel
 * @author comrad
 */
public class ArticleView extends AbstractPageTitleView {
    
    private Long nodeId;
    private String mainTitle;
    private String alternativeTitle1;
    private String alternativeTitle2;
    private String alternativeTitle3;
    
    private String content;
    
    private final List<Tag> tags = new ArrayList<>(20);
    private final Map<AttachmentGroup, List<Attachment>> attachments = new HashMap<>();
    private final List<Comment> comments = new ArrayList<>(10);

    @Override
    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }
    
    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getAlternativeTitle1() {
        return alternativeTitle1;
    }

    public void setAlternativeTitle1(String alternativeTitle1) {
        this.alternativeTitle1 = alternativeTitle1;
    }

    public String getAlternativeTitle2() {
        return alternativeTitle2;
    }

    public void setAlternativeTitle2(String alternativeTitle2) {
        this.alternativeTitle2 = alternativeTitle2;
    }

    public String getAlternativeTitle3() {
        return alternativeTitle3;
    }

    public void setAlternativeTitle3(String alternativeTitle3) {
        this.alternativeTitle3 = alternativeTitle3;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Map<AttachmentGroup, List<Attachment>> getAttachments() {
        return attachments;
    }
    
    public List<Attachment> getVideos() {
        return getAttachments().get(AttachmentGroup.VIDEO);
    }
    
    public List<Attachment> getScreenshots() {
        return getAttachments().get(AttachmentGroup.IMAGE);
    }    
    
    public List<Attachment> getWebsites() {
        return getAttachments().get(AttachmentGroup.WEBSITE);
    }
    
    public List<Comment> getComments() {
        return comments;
    }    

    @Override
    public String getPageTitle() {
        return getMainTitle();
    }
    
}
