package de.holarse.backend.views;

import de.holarse.backend.db.News;
import java.time.OffsetDateTime;


public class NewsView implements PageTitleView {

    private final Long nodeId;
    private final String title;
    private final String content;
    private final OffsetDateTime created;
    private final OffsetDateTime updated;
    private final String url;

    public NewsView(News news) {
        this.nodeId = news.getNodeId();
        this.title = news.getTitle();
        this.content = news.getContent();
        
        this.created = news.getCreated();
        this.updated = news.getUpdated();
        
        this.url = "/news/" + news.getSlug();
    }

    public String getUrl() {
        return url;
    }
    
    public Long getNodeId() {
        return nodeId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public OffsetDateTime getCreated() {
        return created;
    }

    public OffsetDateTime getUpdated() {
        return updated;
    }
}
