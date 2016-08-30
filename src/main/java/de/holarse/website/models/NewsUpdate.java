package de.holarse.website.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name = "news_updates")
public class NewsUpdate extends EntityBase {
    
    @OneToOne
    private News news;
    
    @Column(length = 8192)
    private String content;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
