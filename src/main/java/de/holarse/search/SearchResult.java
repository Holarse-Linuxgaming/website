package de.holarse.search;

/**
 * Projection result für Suche über Repository
 * @author comrad
 */
public interface SearchResult {
    
    Long getId();
    String getTitle();
    String getSubtitles();    
    String getTags();
    String getUrl();
    String getContent();    
    
}
