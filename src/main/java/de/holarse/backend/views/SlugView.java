package de.holarse.backend.views;

/**
 * Definiert 
 * @author comrad
 */
public interface SlugView {

    /**
     * Das eindeutige Erkennungsmerkmal für diese Entity
     * @return 
     */
    String getSlug();
    
    /**
     * Die URL, die sich aus dem Slug ergibt
     * @return 
     */
    String getUrl();
    
}
