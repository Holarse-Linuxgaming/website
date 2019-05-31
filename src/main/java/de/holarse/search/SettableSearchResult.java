package de.holarse.search;

import java.util.List;

public interface SettableSearchResult extends SearchResult {

    void setId(Long id);
    void setTitle(String title);    
    void setAlternativeTitles(List<String> alternativeTitles);
    void setTags(List<String> tags);
    void setUrl(String url);
    void setContent(String content);
    
}
