package de.holarse.backend.api;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;

@JacksonXmlRootElement(localName = "state")
public class State implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JacksonXmlProperty  
    private Boolean locked;
    @JacksonXmlProperty  
    private Boolean commentable;
    @JacksonXmlProperty  
    private Boolean published;
    @JacksonXmlProperty  
    private Boolean deleted;
    @JacksonXmlProperty  
    private Boolean archived;

    @JacksonXmlProperty  
    private Boolean ftp;
    @JacksonXmlProperty      
    private Boolean ftpTools;
    @JacksonXmlProperty      
    private String releaseDate;

    public Boolean getFtp() {
        return ftp;
    }

    public void setFtp(Boolean ftp) {
        this.ftp = ftp;
    }

    public Boolean getFtpTools() {
        return ftpTools;
    }

    public void setFtpTools(Boolean ftpTools) {
        this.ftpTools = ftpTools;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getCommentable() {
        return commentable;
    }

    public void setCommentable(Boolean commentable) {
        this.commentable = commentable;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }
    
}
