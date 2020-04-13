package de.holarse.backend.views;

import de.holarse.search.SearchResult;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class SearchResultView implements ViewModel {

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
    
    public List<String> getSubtitles() {
        if (StringUtils.isBlank(result.getSubtitles()))
            return List.of("");
        
        return List.of(result.getSubtitles().split(";"));
    }
    
    public List<String> getTags() {
        if (StringUtils.isBlank(result.getTags()))
            return List.of("");
        
        return List.of(result.getTags().split(";"));
    }
    
    public String getTeaser() {
        return StringUtils.abbreviate(result.getContent(), 250);
    }
   
    
}
