package de.holarse.web.search;

/**
 * Projection result für suche über Repository
 * @author comrad
 */
public interface SearchResult {
    
    Long getId();
    String getTitle();
    String getNodeType();
    String getAlternativeTitle();
    String getContent();
    
}
