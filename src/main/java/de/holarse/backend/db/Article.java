package de.holarse.backend.db;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="articles")
@Entity
public class Article extends CommentableNode {
    
    private String title;
    private String alternativeTitles;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlternativeTitles() {
        return alternativeTitles;
    }

    public void setAlternativeTitles(String alternativeTitles) {
        this.alternativeTitles = alternativeTitles;
    }
    
}
