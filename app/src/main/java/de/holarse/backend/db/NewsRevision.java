package de.holarse.backend.db;

import jakarta.persistence.*;

@Table(name = "news_revisions")
@Entity
public class NewsRevision extends RevisionedNode {

    private static final long serialVersionUID = 1L;

    @Column(length = 255)
    private String title;
    @Column(length = 16384)
    private String content;

    @Column(length = 512)
    private String teaser;

    @OneToOne(mappedBy = "nodeRevision")
    private News news;    
    
    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="news_category_id", referencedColumnName = "id")
    private NewsCategory newsCategory;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeaser() { return teaser; }

    public void setTeaser(String teaser) { this.teaser = teaser;  }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewsCategory getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(NewsCategory newsCategory) {
        this.newsCategory = newsCategory;
    }

    @Override
    public String toString() {
        return "NewsRevision{id=" + getId() + ", "+ "title=" + title + ", content=" + content + ", teaser=" + teaser + ", newsCategory=" + newsCategory + '}';
    }

}
