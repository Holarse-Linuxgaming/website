package de.holarse.backend.export;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@JacksonXmlRootElement(localName = "article")
public class Article {

    @JacksonXmlProperty(localName="uid", isAttribute = true)
    private Long uid;

    @JacksonXmlProperty(localName="revision")
    private Revision revision;
    
    @JacksonXmlProperty(localName="state")
    private State state;
    
    @JacksonXmlProperty(localName="content")
    private Content content;
    
    @JacksonXmlProperty(localName="title")
    @JacksonXmlElementWrapper(localName = "titles")
    private List<Title> titles = new ArrayList<>();
    
    @JacksonXmlProperty(localName="tag")
    @JacksonXmlElementWrapper(localName = "tags")
    private List<Tag> tags = new ArrayList<>();

    @JacksonXmlProperty(localName="attachment")
    @JacksonXmlElementWrapper(localName = "attachments")    
    private List<Attachment> attachments = new ArrayList<>();


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
    
}
