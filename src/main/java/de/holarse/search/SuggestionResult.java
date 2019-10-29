package de.holarse.search;

public interface SuggestionResult {

    /**
     * Das anzeigbare Label
     * @return 
     */
    String getWlabel();
    
    /**
     * Der Typ (1 == Titel, 2 == Tag)
     * @return 
     */
    int getWtype();
    
}
