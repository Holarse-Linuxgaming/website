package de.holarse.search;

import de.holarse.backend.db.Searchable;
import java.util.List;

public interface SearchEngine {

    List<SearchResult> search(String query);
    void update(Searchable searchable);
    void delete(Searchable searchable);
    
}
