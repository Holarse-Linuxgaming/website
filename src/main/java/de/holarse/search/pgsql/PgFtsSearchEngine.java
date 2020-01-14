package de.holarse.search.pgsql;

import de.holarse.backend.db.Searchable;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.TagGroup;
import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.search.SearchEngine;
import de.holarse.search.SearchResult;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("pgsql")
public class PgFtsSearchEngine implements SearchEngine {

    @Autowired
    private SearchRepository searchRepository;
    
    @Override
    public List<SearchResult> search(final String query) {
        return searchRepository.search(query);
    }

    @Override
    public List<SearchResult> searchByTags(final Collection<Tag> tags, final String query) {
        // Tags in das von Postgres nötige Format bringen
        String _tags = tags.stream().map(t -> t.getName()).collect(Collectors.joining(";"));
        
        if (StringUtils.isBlank(query)) 
            return searchRepository.searchTags(_tags);
        
        return searchRepository.search(query, _tags);
    }

    @Override
    public void update(final Searchable searchable) throws IOException {
        searchRepository.update();
    }

    @Override
    public void update(final Iterable<? extends Searchable> searchables) throws IOException {
        searchRepository.update();
    }

    @Override
    public void updateTags(final Iterable<TagGroup> tagGroups) throws IOException {
        searchRepository.update();
    }

    @Override
    public void updateEverything(final Iterable<? extends Searchable> searchables, Iterable<TagGroup> tagGroups) throws IOException {
        searchRepository.update();
    }

    @Override
    public void delete(final Searchable searchable) throws IOException {
        searchRepository.update();
    }
    
}
