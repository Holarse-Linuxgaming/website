package de.holarse.backend.db;

import de.holarse.backend.db.types.NewsCategory;
import de.holarse.backend.db.types.NewsType;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

@Table(name = "news")
@Entity
public class News extends BranchableNode implements Frontpagable, Searchable {
    
    private String title;
    private String subtitle;
    @Enumerated(EnumType.STRING)
    private NewsCategory category;
    private String source;
    @Enumerated(EnumType.STRING)
    private NewsType newsType;

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

    @Override
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

    @Override
    public Set<String> getAlternativeTitles() {
        return new HashSet<>();
    }

    @Override
    public Set<Tag> getTags() {
        return new HashSet<>();
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
    public String getType() {
        return "news";
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    @Override
    public String getTeaserImage() {
        return null;
    }   
}
