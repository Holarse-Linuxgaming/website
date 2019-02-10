package de.holarse.backend.views;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsView extends AbstractPageTitleView {

    private final List<SearchResultView> results = new ArrayList<>(250);
    private String searchTerm;

    public SearchResultsView(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    
    @Override
    public String getPageTitle() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("Suche nach '").append(searchTerm).append("' mit ").append(count()).append(" Ergebnissen");        
        return buffer.toString();
    }
    
    public int count() {
        return results.size();
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<SearchResultView> getResults() {
        return results;
    }
    
}
