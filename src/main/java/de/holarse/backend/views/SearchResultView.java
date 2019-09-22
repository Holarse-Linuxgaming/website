package de.holarse.backend.views;

import de.holarse.search.SearchResult;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class SearchResultView {

    private final SearchResult result;
    
    public SearchResultView(final SearchResult result) {
        this.result = result;
    }
    
    public String getUrl() {
        return result.getUrl();
    }
    
    public String getTitle() {
        return result.getTitle();
    }
    
    public String getAlternativeTitles() {
        if (result.getAlternativeTitles() == null)
            return "";
        
        return result.getAlternativeTitles().stream().collect(Collectors.joining(","));
    }
    
    public List<String> getTags() {
        return result.getTags();
    }
    
    public String getTeaser() {
        return StringUtils.abbreviate(result.getContent(), 250);
    }
   
    
}
