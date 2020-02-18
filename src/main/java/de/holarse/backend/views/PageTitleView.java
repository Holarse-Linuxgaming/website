package de.holarse.backend.views;

/**
 * Einheitliches Interface für alle anzeigbaren Nodes
 */
public interface PageTitleView extends View {
    
    default String getPageTitle() {
        return "Eure deutschsprachige Linuxspiele-Community";
    }
    
    String getUrl();
    String getEditUrl();
    
}
