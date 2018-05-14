package de.holarse.search;

/**
 * Projection result für suche über Repository
 * @author comrad
 */
public interface SearchResult {
    
    Long getId();    
    String getTitle();
    String getAlternativeTitle();    
    String getTags();
    String getUrl();
    String getNodeType();
    String getContent();    
    
}
