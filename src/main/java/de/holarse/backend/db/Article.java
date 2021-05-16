package de.holarse.backend.db;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.FetchType;
import org.apache.commons.lang3.StringUtils;

@Table(name="articles")
@Entity
public class Article extends BranchableNode implements Frontpagable, Searchable {
    
    private String title;
    private String alternativeTitle1;
    private String alternativeTitle2;
    private String alternativeTitle3;
    private String alternativeTitle4;
    private String alternativeTitle5;  
    
    @Column(name = "title_lang", columnDefinition = "character varying(12) default 'english'")
    private String titleLang;
    @Column(name = "alternativetitle1_lang", columnDefinition = "character varying(12) default 'english'")
    private String alternativeTitle1Lang;
    @Column(name = "alternativetitle2_lang", columnDefinition = "character varying(12) default 'english'")
    private String alternativeTitle2Lang;
    @Column(name = "alternativetitle3_lang", columnDefinition = "character varying(12) default 'english'")
    private String alternativeTitle3Lang;
    @Column(name = "alternativetitle4_lang", columnDefinition = "character varying(12) default 'english'")
    private String alternativeTitle4Lang;
    @Column(name = "alternativetitle5_lang", columnDefinition = "character varying(12) default 'english'")
    private String alternativeTitle5Lang;      
    
    @Transient
    private final Set<String> alternativeTitles = new HashSet<>();

    @Transient
    private String url;
    
    @Transient
    private String urlid;
    
    @ManyToMany(fetch = FetchType.LAZY)
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
    
    @Deprecated
    @PostLoad
    private void articlePostLoad() {
        this.url = "/wiki/" + getSlug();
        this.urlid = "/wiki/" + getId();
        this.teaser = StringUtils.abbreviate(getContent(), 100);
        this.nodeType = NodeType.ARTICLE;
        
        if (StringUtils.isNoneBlank(alternativeTitle1)) alternativeTitles.add(alternativeTitle1);        
        if (StringUtils.isNoneBlank(alternativeTitle2)) alternativeTitles.add(alternativeTitle2);        
        if (StringUtils.isNoneBlank(alternativeTitle3)) alternativeTitles.add(alternativeTitle3);
        if (StringUtils.isNoneBlank(alternativeTitle4)) alternativeTitles.add(alternativeTitle4);
        if (StringUtils.isNoneBlank(alternativeTitle5)) alternativeTitles.add(alternativeTitle5);        
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

    public String getAlternativeTitle4() {
        return alternativeTitle4;
    }

    public void setAlternativeTitle4(String alternativeTitle4) {
        this.alternativeTitle4 = alternativeTitle4;
    }

    public String getAlternativeTitle5() {
        return alternativeTitle5;
    }

    public void setAlternativeTitle5(String alternativeTitle5) {
        this.alternativeTitle5 = alternativeTitle5;
    }

    public String getTitleLang() {
        return titleLang;
    }

    public void setTitleLang(String titleLang) {
        this.titleLang = titleLang;
    }

    public String getAlternativeTitle1Lang() {
        return alternativeTitle1Lang;
    }

    public void setAlternativeTitle1Lang(String alternativeTitle1Lang) {
        this.alternativeTitle1Lang = alternativeTitle1Lang;
    }

    public String getAlternativeTitle2Lang() {
        return alternativeTitle2Lang;
    }

    public void setAlternativeTitle2Lang(String alternativeTitle2Lang) {
        this.alternativeTitle2Lang = alternativeTitle2Lang;
    }

    public String getAlternativeTitle3Lang() {
        return alternativeTitle3Lang;
    }

    public void setAlternativeTitle3Lang(String alternativeTitle3Lang) {
        this.alternativeTitle3Lang = alternativeTitle3Lang;
    }

    public String getAlternativeTitle4Lang() {
        return alternativeTitle4Lang;
    }

    public void setAlternativeTitle4Lang(String alternativeTitle4Lang) {
        this.alternativeTitle4Lang = alternativeTitle4Lang;
    }

    public String getAlternativeTitle5Lang() {
        return alternativeTitle5Lang;
    }

    public void setAlternativeTitle5Lang(String alternativeTitle5Lang) {
        this.alternativeTitle5Lang = alternativeTitle5Lang;
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
