package de.holarse.search.pgsqlfts;

import de.holarse.backend.db.Searchable;
import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.search.SearchEngine;
import de.holarse.search.SearchResult;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("postgres")
public class PostgresFtsSearchEngine implements SearchEngine {

    @Autowired
    SearchRepository searchRepository;
    
    @Override
    public List<SearchResult> search(final String query) {
        return searchRepository.search(breakAndJoin(query));
    }

    @Override
    public void update(final Searchable searchable) {
        searchRepository.update();
    }
    
    @Override
    public void update(final Collection<Searchable> searchables) {
        searchRepository.update();
    }    

    @Override
    public void delete(final Searchable searchable) {
        searchRepository.update();
    }
    
    /**
     * Bricht ein Query auseinander und verbindet es mit &
     * @param query
     * @return 
     */
    protected String breakAndJoin(final String query) {
        final StringJoiner sj = new StringJoiner("&");
        
        for (final String q : query.split(" ")) {
            sj.add(q);
        }
        
        return sj.toString();
    }    
    
}
