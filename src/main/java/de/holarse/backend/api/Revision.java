package de.holarse.backend.api;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.io.Serializable;
import java.util.Date;

@JacksonXmlRootElement(localName = "revision")
public class Revision implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JacksonXmlProperty
    private Date created;
    @JacksonXmlProperty
    private String author;
    @JacksonXmlProperty
    private long revisionId;
    @JacksonXmlProperty
    private String changelog;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getRevisionId() {
        return revisionId;
    }

    public void setRevisionId(long revisionId) {
        this.revisionId = revisionId;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }
    
}
