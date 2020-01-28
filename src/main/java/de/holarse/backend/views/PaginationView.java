package de.holarse.backend.views;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * View für die Erstellung einer Pagination. Liefert eine Liste mit "Knöpfen",
 * die zum Aufbau der Paginationbar geeignet sind.
 * @author comrad
 */
public class PaginationView {

    private final String baseUrl;
    private final int currentPage;
    private final long maxPage;
    
    private final static String PAGE_PARAM = "page";
    private final static String PAGESIZE_PARAM = "pageSize";

    private final static int PAGESIZE_MIN = 5;
    private final static int PAGESIZE_MAX = 100;
    private final static int PAGESIZE_DEFAULT = 30;
    
    private final int pageSize;

    /**
     * Dieses Element wird an den View übergeben.
     */
    private PaginationItemView backButton;
    private final List<PaginationItemView> paginationBar = new ArrayList<>(5);
    private PaginationItemView nextButton;
    
    /**
     * 
     * @param baseUrl BasisURL des Links, z.B. /news
     * @param currentPage Die aktuelle Seitenzahl
     * @param maxPage Beschreibt die maximale Seitenzahl
     * @param pageSize Beschreibt die Anzahl der Elemente auf einer Seite
     */
    public PaginationView(final String baseUrl, final int currentPage, final long maxPage, final int pageSize) {
        this.baseUrl = baseUrl;
        this.currentPage = currentPage;
        this.maxPage = maxPage;
        this.pageSize = getBoundedPageSize(pageSize); // Einmal berechnen am Anfang reicht
        
        renderBar();
    }
    
    private void renderBar() {
        // Linke Zurück-Navigation
        backButton = new PaginationItemView("Zurück", String.format("%s?%s=%d&%s=%d", baseUrl, PAGE_PARAM, getPreviousPage(), PAGESIZE_PARAM, pageSize), true, canGoBack());

        // Den Anfang hinten rausschieben
        int lowerMissing = (currentPage - 3 < 0 ? Math.abs(currentPage - 3) : 0);
        // Seiten hinzufügen
        IntStream.rangeClosed(currentPage - 2, currentPage + 2 + lowerMissing).forEach(i -> {
            
            if (i > 0 && i < maxPage) {
                paginationBar.add(new PaginationItemView(String.valueOf(i), String.format("%s?page=%d&pageSize=%d", baseUrl, i, pageSize), false, i == currentPage));
            }
            
        });
                
        // Rechte Weiter-Navigation
        nextButton = new PaginationItemView("Weiter", String.format("%s?%s=%d&%s=%d", baseUrl, PAGE_PARAM, getNextPage(), PAGESIZE_PARAM, pageSize), true, canGoFurther());
    }
    
    /**
     * Gibt die Pagesize innerhalb der erlaubten Grenzen zurück
     * @return 
     */
    private int getBoundedPageSize(final int psize) {
        if (psize < PAGESIZE_MIN)
            return PAGESIZE_MIN;
        
        if (psize > PAGESIZE_MAX)
            return PAGESIZE_MAX;
        
        return psize;
    }
    
    private boolean canGoBack() {
        return getPreviousPage() < currentPage;
    }
    
    private boolean canGoFurther() {
        return getNextPage() > currentPage;
    }
    
    private int getPreviousPage() {
        if (currentPage - 1 <= 0)
            return 1;
        
        return currentPage - 1;
    }
    
    /**
     * Die Seite für das Spring Data-Pageable-System, das bei 0 beginnt.
     * @return Die aktuelle Seite ab 0
     */
    public int getPageRequestPage() {
        return (currentPage - 1 < 0 ? 0 : currentPage - 1);
    }
    
    /**
     * Ermittelt die nächste Seite
     * @return 
     */
    private long getNextPage() {
        if (currentPage + 1 > maxPage)
            return maxPage;
        
        return currentPage + 1;
    }

    /**
     * Die Liste der Seitenbuttons
     * @return 
     */
    public List<PaginationItemView> getPaginationBar() {
        return paginationBar;
    }

    /**
     * Aktuelle Seite bei Zählweise ab 1
     * @return 
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Höchste Seitenzahl
     * @return 
     */
    public long getMaxPage() {
        return maxPage;
    }
    
    /**
     * Ermittelte Ergebnismenge je Seite, bereits boundsbereinigt
     * @return 
     */
    public int getPageSize() {
        return pageSize;
    }
    
    public PaginationItemView getBackButton() {
        return backButton;
    }
    
    public PaginationItemView getNextButton() {
        return nextButton;
    }
    
    
    
}
