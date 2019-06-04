package de.holarse.backend.views;

import java.net.http.HttpRequest;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponents;
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
        
        final UriComponentsBuilder builder = UriComponentsBuilder.fromPath(url);
        
        return builder.build().toUriString();
    }
    
    
    
}
