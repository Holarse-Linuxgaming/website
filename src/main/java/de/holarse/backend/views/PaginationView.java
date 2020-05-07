package de.holarse.backend.views;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * View für die Erstellung einer Pagination. Liefert eine Liste mit "Knöpfen",
 * die zum Aufbau der Paginationbar geeignet sind.
 * 
 * Diese wird in serverseitig gerenderten Views benutzt.
 * @author comrad
 */
public class PaginationView {

    Logger logger = LoggerFactory.getLogger(PaginationView.class);

    private final String baseUrl;
    private final int currentPage;
    private final long maxItems;
    private final long maxPage;
    
    private final static String PAGE_PARAM = "page";
    private final static String PAGESIZE_PARAM = "size";
    
    private final static int PAGESIZE_MIN = 5;
    private final static int PAGESIZE_MAX = 100;
        
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
     * @param maxItems Beschreibt die maximale Ergebnismenge
     * @param pageSize Beschreibt die Anzahl der Elemente auf einer Seite
     */
    public PaginationView(final String baseUrl, final int currentPage, final long maxItems, final int pageSize) {
        this.baseUrl = baseUrl;
        this.currentPage = currentPage;
        this.maxItems = maxItems;
        this.pageSize = getBoundedPageSize(pageSize); // Einmal berechnen am Anfang reicht
        this.maxPage = (long) Math.ceil(maxItems / (float) this.pageSize);
        
        renderBar();
    }

    public PaginationView(final String baseUrl, final Pageable pageable, final long maxItems) {
        this(baseUrl, pageable.getPageNumber(), maxItems, pageable.getPageSize());
    }
    
    public PageRequest getPageable() {
        return PageRequest.of(getPageRequestPage(), pageSize);
    }

    public PageRequest getPageable(final Sort sort) {
        return PageRequest.of(getPageRequestPage(), pageSize, sort);
    }
    
    private String buildUrl(final long page) {
        if (baseUrl.contains("?"))
            return String.format("%s&%s=%d&%s=%d", baseUrl, PAGE_PARAM, page, PAGESIZE_PARAM, pageSize);
        
        return String.format("%s?%s=%d&%s=%d", baseUrl, PAGE_PARAM, page, PAGESIZE_PARAM, pageSize);
    }
    
    private void renderBar() {
        // Linke Zurück-Navigation
        backButton = new PaginationItemView("Zurück", buildUrl(getPreviousPage()), true, canGoBack());

        // Den Anfang hinten rausschieben
        int lowerMissing = (currentPage - 3 < 0 ? Math.abs(currentPage - 3) : 0);
        // Seiten hinzufügen
        IntStream.rangeClosed(currentPage - 2, currentPage + 2 + lowerMissing).forEach(i -> {
            
            if (i > 0 && i <= maxPage) {
                paginationBar.add(new PaginationItemView(String.valueOf(i), buildUrl(i), false, i == currentPage));
            }
            
        });
                
        // Rechte Weiter-Navigation
        nextButton = new PaginationItemView("Weiter", buildUrl(getNextPage()) , true, canGoFurther());
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
    
    private long getPreviousPage() {
        if (currentPage - 1 < 1)
            return 1;
        
        return currentPage - 1;
    }
    
    /**
     * Die Seite für das Spring Data-Pageable-System, das bei 0 beginnt.
     * Relevant um passende Queries auszuführen
     * @return Die aktuelle Seite ab 0
     */
    public int getPageRequestPage() {
        return currentPage - 1 < 0 ? 0 : currentPage - 1;
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
     * Maximale Elementanzahl
     * @return 
     */
    public long getMaxItems() {
        return maxItems;
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
