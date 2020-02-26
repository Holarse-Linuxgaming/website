package de.holarse.web.seo;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "url")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"url", "lastModified", "changeFreq", "priority"})
public class SiteMapNode implements Serializable {

    private Long id;
    private String slug;
    private OffsetDateTime created;
    private OffsetDateTime updated;
    private String type;
    // Base URL without Path
    // e.g. http://example.com
    private String baseURL = "";

    static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");

    // Needed for JAXB marshall
    public SiteMapNode() {}

    public SiteMapNode(
            Long id, String slug, OffsetDateTime created, OffsetDateTime updated, String type) {
        this.id = id;
        this.slug = slug;
        this.created = created;
        this.updated = updated;
        this.type = type;
    }

    public SiteMapNode(Object[] node) {
        this.id = ((BigInteger) node[0]).longValue();
        this.slug = (String) node[1];
        this.created = (OffsetDateTime) node[2];
        this.updated = (OffsetDateTime) node[3];
        this.type = (String) node[4];
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getBaseURL() {
        return this.baseURL;
    }

    public Long getId() {
        return this.id;
    }

    public String getSlug() {
        return this.slug;
    }

    public OffsetDateTime getCreated() {
        return this.created;
    }

    public OffsetDateTime getUpdated() {
        return this.updated;
    }

    public String getType() {
        return this.type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    public void setUpdated(OffsetDateTime updated) {
        this.updated = updated;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "loc")
    public String getUrl() {
        String type = this.type;
        if (type.equals("article")) {
            type = "wiki";
        }
        // URL: baseURL/type/slug
        // e.g. http://example.com/wiki/example
        return String.format("%s/%s/%s", this.baseURL, type, this.slug);
    }

    @XmlElement(name = "lastmod")
    public String getLastModified() {
        OffsetDateTime lastModified = this.updated;
        if (lastModified == null && this.created == null) {
            return "";
        } else if (lastModified == null) {
            lastModified = this.created;
        }

        return lastModified.toInstant().atZone(ZoneId.of("UTC")).format(SiteMapNode.dateTimeFormat);
    }

    @XmlElement(name = "changefreq")
    public String getChangeFreq() {
        return "never";
    }

    @XmlElement(name = "priority")
    public float getPriority() {
        return 0.5f;
    }
}
