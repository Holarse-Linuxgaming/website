package de.holarse.search;

import java.util.List;

/**
 * Projection result für suche über Repository
 * @author comrad
 */
public interface SearchResult {
    
    Long getId();
    String getTitle();
    List<String> getAlternativeTitles();    
    List<String> getTags();
    String getUrl();
    String getContent();    
    
}
