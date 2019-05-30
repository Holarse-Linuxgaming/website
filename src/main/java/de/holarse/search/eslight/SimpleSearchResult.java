package de.holarse.search.eslight;

import de.holarse.search.SettableSearchResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleSearchResult implements SettableSearchResult {
    
    private long id;
    private String title;
    private final List<String> alternativeTitles = new ArrayList<>();
    private final List<String> tags = new ArrayList<>();
    private String content;
    private String url;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public List<String> getAlternativeTitles() {
        return Collections.unmodifiableList(alternativeTitles);
    }

    @Override
    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    @Override
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setAlternativeTitles(final List<String> alternativeTitles) {
        this.alternativeTitles.addAll(alternativeTitles);
    }

    @Override
    public void setTags(List<String> tags) {
        this.tags.addAll(tags);
    }
    
}
