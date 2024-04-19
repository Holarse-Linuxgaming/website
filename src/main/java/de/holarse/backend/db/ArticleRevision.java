package de.holarse.backend.db;

import jakarta.persistence.*;

@Table(name = "article_revisions")
@Entity
public class ArticleRevision extends RevisionedNode {
    
    private static final long serialVersionUID = 1L;

    @Column(length = 512)
    private String title1;
    @Column(length = 12, name = "title1_lang")
    private String title1Lang;    
    
    @Column(length = 512)
    private String title2;
    @Column(length = 12, name = "title2_lang")
    private String title2Lang;        
    
    @Column(length = 512)
    private String title3;
    @Column(length = 12, name = "title3_lang")
    private String title3Lang;        
    
    @Column(length = 512)
    private String title4;
    @Column(length = 12, name = "title4_lang")
    private String title4Lang;        
    
    @Column(length = 512)
    private String title5;
    @Column(length = 12, name = "title5_lang")
    private String title5Lang;        
    
    @Column(length = 512)
    private String title6;
    @Column(length = 12, name = "title6_lang")
    private String title6Lang;        
    
    @Column(length = 512)
    private String title7;
    @Column(length = 12, name = "title7_lang")
    private String title7Lang;        
    
    @Column(length = 16384)    
    private String content;

    @Column(length = 512)
    private String teaser;

    @OneToOne(mappedBy = "nodeRevision")
    private Article article;

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getTitle4() {
        return title4;
    }

    public void setTitle4(String title4) {
        this.title4 = title4;
    }

    public String getTitle5() {
        return title5;
    }

    public void setTitle5(String title5) {
        this.title5 = title5;
    }

    public String getTitle6() {
        return title6;
    }

    public void setTitle6(String title6) {
        this.title6 = title6;
    }

    public String getTitle7() {
        return title7;
    }

    public void setTitle7(String title7) {
        this.title7 = title7;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getTitle1Lang() {
        return title1Lang;
    }

    public void setTitle1Lang(String title1Lang) {
        this.title1Lang = title1Lang;
    }

    public String getTitle2Lang() {
        return title2Lang;
    }

    public void setTitle2Lang(String title2Lang) {
        this.title2Lang = title2Lang;
    }

    public String getTitle3Lang() {
        return title3Lang;
    }

    public void setTitle3Lang(String title3Lang) {
        this.title3Lang = title3Lang;
    }

    public String getTitle4Lang() {
        return title4Lang;
    }

    public void setTitle4Lang(String title4Lang) {
        this.title4Lang = title4Lang;
    }

    public String getTitle5Lang() {
        return title5Lang;
    }

    public void setTitle5Lang(String title5Lang) {
        this.title5Lang = title5Lang;
    }

    public String getTitle6Lang() {
        return title6Lang;
    }

    public void setTitle6Lang(String title6Lang) {
        this.title6Lang = title6Lang;
    }

    public String getTitle7Lang() {
        return title7Lang;
    }

    public void setTitle7Lang(String title7Lang) {
        this.title7Lang = title7Lang;
    }
    
}
