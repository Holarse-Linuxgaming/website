package de.holarse.search;

import de.holarse.backend.db.Searchable;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.TagGroup;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface SearchEngine {

    List<SearchResult> search(String query, Pageable pageable);
    long searchCount(String query);
    
    List<SearchResult> searchByTags(Collection<Tag> tags, String query);
    
    void update(Searchable searchable) throws IOException;
    void update(Iterable<? extends Searchable> searchables) throws IOException;
    void updateTags(Iterable<TagGroup> tagGroups) throws IOException;
    void updateEverything(Iterable<? extends Searchable> searchables, Iterable<TagGroup> tagGroups) throws IOException;
    void delete(Searchable searchable) throws IOException;
    
}
