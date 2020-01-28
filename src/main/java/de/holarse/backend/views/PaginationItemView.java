package de.holarse.backend.views;

/**
 * Stellt ein Element in der Pagination dar, unterscheidet zwischen
 * einem Navigationselement (links und rechts) oder einem Seiten-Button
 * @author comrad
 */
public class PaginationItemView {
    
    private final boolean navElement;
    private final String label;
    private final String url;
    
    private final boolean active;

    public PaginationItemView(final String label, final String url, final boolean navElement, final boolean active) {
        this.navElement = navElement;
        this.label = label;
        this.url = url;
        
        this.active = active;
    }

    public boolean isNavElement() {
        return navElement;
    }

    public String getLabel() {
        return label;
    }

    public String getUrl() {
        return url;
    }

    public boolean isActive() {
        return active;
    }
    
}
