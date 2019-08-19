package de.holarse.backend.views;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * View für die Erstellung einer Pagination
 * @author comrad
 */
public class PaginationView<T> {

    private final Page<T> page;
    private final String url;
    
    public PaginationView(final Page<T> page, final String url) {
        this.page = page;
        this.url = url;
    }
    
    public boolean hasPrevious() {
        return page.hasPrevious();
    }
    
    public boolean hasNext() {
        return page.hasNext();
    }
    
    public List<T> getContent() {
        return page.getContent();
    }
    
    public String getPreviousUrl() {
        final Pageable previousPage = page.previousPageable();
        // TODO Url korrigieren, sie müssen die Parameter enthalten
        final UriComponentsBuilder builder = UriComponentsBuilder.fromPath(url);
        
        return builder.build().toUriString();
    }
    
    
    
}
