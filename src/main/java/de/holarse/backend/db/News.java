package de.holarse.backend.db;

import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.StringUtils;

@Table(name = "news")
@Entity
public class News extends SluggableNode implements Frontpagable, Searchable {
    
    private String title;
    private String subtitle;
    private NewsCategory category;
    private String source;

    @Transient
    private String url;
    
    @Transient
    private String urlid;   
    
    @Transient
    private String teaser;
    
    @Transient
    private NodeType nodeType;
    
    @Override    
    public String getUrl() {
        return url;
    }    

    public String getUrlid() {
        return urlid;
    }
    
    @PostLoad
    private void newsPostLoad() {
        this.url = "/news/" + getSlug();
        this.urlid = "/news/" + getId();
        this.teaser = StringUtils.abbreviate(getContent(), 100);
        this.nodeType = NodeType.NEWS;
    }    
    
    @Override
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

    public NewsCategory getCategory() {
        return category;
    }

    public void setCategory(NewsCategory category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public Long getNodeId() {
        return getId();
    }

    @Override
    public String getTeaser() {
        return teaser;
    }

    @Override
    public NodeType getNodeType() {
        return nodeType;
    }
    
    @Override
    public String getIndex() {
        return getNodeType().toString();
    }
    
}
