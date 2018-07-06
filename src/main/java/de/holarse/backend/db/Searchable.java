package de.holarse.backend.db;

import java.util.Set;

public interface Searchable {
 
    Long getId();
    String getType();
    
    String getTitle();
    Set<String> getAlternativeTitles();
    String getContent();
    Set<Tag> getTags();
    
    String getUrl();
    
}
