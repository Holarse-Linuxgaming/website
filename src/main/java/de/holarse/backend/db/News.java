package de.holarse.backend.db;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "news")
@Entity
public class News extends SluggableNode {
    
    private String title;
    private String subtitle;
    private NewsCategory category;
    private String source;

    @Override
    public String getUrl() {
        return "/news/" + getId();
    }
    
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
    
}
