package de.holarse.backend.views;

/**
 * Interface für alle Nodes mit renderbarem Content
 */
public interface ContentView {

    void setContent(String content);
    String getContent();
    
    void setFormattedContent(String formattedContent);
    String getFormattedContent();
    
    void setPlainContent(String plainContent);
    String getPlainContent();
    
    void setTeaser(String teaser);
    String getTeaser();    

}