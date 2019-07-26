package de.holarse.backend.db;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

@Table(name="articles")
@Entity
public class Article extends BranchableNode implements Frontpagable, Searchable {
    
    private String title;
    private String alternativeTitle1;
    private String alternativeTitle2;
    private String alternativeTitle3;
    
    @Transient
    private final Set<String> alternativeTitles = new HashSet<>();

    @Transient
    private String url;
    
    @Transient
    private String urlid;
    
    @ManyToMany
    @OrderBy(value = "name")
    private final Set<Tag> tags = new HashSet<>();

    @Transient
    private String teaser;
    
    @Transient
    private NodeType nodeType;    

    @Override
    public Set<Tag> getTags() {
        return tags;
    }

    @Override
    public String getUrlid() {
        return urlid;
    }

    @Override
    public Set<String> getAlternativeTitles() {
        return alternativeTitles;
    }
    
    @Override    
    public String getUrl() {
        return url;
    }    
    
    @PostLoad
    private void articlePostLoad() {
        this.url = "/html/" + getSlug();
        this.urlid = "/html/" + getId();
        this.teaser = StringUtils.abbreviate(getContent(), 100);
        this.nodeType = NodeType.ARTICLE;
        
        if (StringUtils.isNoneBlank(alternativeTitle1)) alternativeTitles.add(alternativeTitle1);        
        if (StringUtils.isNoneBlank(alternativeTitle2)) alternativeTitles.add(alternativeTitle2);        
        if (StringUtils.isNoneBlank(alternativeTitle3)) alternativeTitles.add(alternativeTitle3);
    }
    
    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    /**
     * Einheitlicher Zugriff auf den Identifier. Ist die Id.
     * @return 
     */
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
        return "articles";
    }
    
    @Override
    public String getTeaserImage() {
        return null;
    }
}
