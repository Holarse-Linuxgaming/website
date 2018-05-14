package de.holarse.backend.db;

public interface Searchable {
 
    Long getId();
    String getIndex();
    
    String getTitle();
    String getContent();
    
}
