package de.holarse.search;

import de.holarse.backend.db.Searchable;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface SearchEngine {

    List<SearchResult> search(String query);
    void update(Searchable searchable) throws IOException;
    void update(Collection<Searchable> searchables) throws IOException;
    void delete(Searchable searchable) throws IOException;
    
}
