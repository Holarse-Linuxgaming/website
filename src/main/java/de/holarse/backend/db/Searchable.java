package de.holarse.backend.db;

import java.util.List;
import java.util.Set;

/**
 * Projection für Suchergebnisse
 * 
 * TODO Verschieben in das Projection-Package
 */
public interface Searchable {
    Long getId();
    String getType();
    
    String getTitle();
    Set<String> getAlternativeTitles();
    String getContent();
    Set<Tag> getTags();
    
    List<Comment> getComments();
    
    String getUrl();
}
