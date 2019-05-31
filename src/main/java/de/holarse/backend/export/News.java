package de.holarse.backend.export;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "news")
public class News {

    @JacksonXmlProperty(localName="uid", isAttribute = true)
    private Long uid;
    
    @JsonIgnore
    private Long vid;

    @JacksonXmlProperty(localName="created", isAttribute = true)
    private Date created;    
    
    @JacksonXmlProperty(localName="revision")
    private Revision revision;
    
    @JacksonXmlProperty(localName="state")
    private State state;
    
    @JacksonXmlProperty(localName="content")
    private Content content;
    
    @JacksonXmlProperty(localName="title")
    private String title;
    
    @JacksonXmlProperty(localName="subtitle")
    private String subtitle;    
    
    @JacksonXmlProperty(localName="category")
    private String category;         
    
    @JacksonXmlProperty(localName="attachment")
    @JacksonXmlElementWrapper(localName = "attachments")    
    private List<Attachment> attachments = new ArrayList<>();

    @JacksonXmlProperty(localName="newstype", isAttribute = true)
    private String newsType;

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    public Revision getRevision() {
        return revision;
    }

    public void setRevision(Revision revision) {
        this.revision = revision;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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
    
}
