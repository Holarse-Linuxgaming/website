package de.holarse.pagination;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Pagination {

    private final int currentPage;
    private final int maxPages;
    
    public Pagination(int currentPage, int maxPages) {
        this.currentPage = currentPage;
        this.maxPages = maxPages % 2 == 0 ? maxPages + 1 : maxPages;
    }
    
    public int getMedian() {
        int med = (maxPages - 1) / 2;
        return med > 0 ? med : 0;
    }
    
    public List<Integer> getList() {
        final List<Integer> result = new ArrayList<>(maxPages);
        
        // Untere Bounds berechnen
        int median = getMedian();
        int remainsLower = median;
        
        int page = currentPage - median;
        
        // Erst nach unten
        while(page > 0 && remainsLower > 0) {
            result.add(page);
            
            // Weiter nach unten
            page++;
            
            // Einen Remain verbraucht
            remainsLower--;
        }

        // Aktuelle Seite einfügen
        result.add(currentPage);
        
        page = currentPage + 1;
        int remainsUpper = median + remainsLower;
        
        // Dann nach oben
        while(remainsUpper > 0) {
            result.add(page);
            
            // Weiter nach oben
            page++;
            
            // Einen Remain verbraucht
            remainsUpper--;
        }
        
        return result;
    }
 
    
    
}
