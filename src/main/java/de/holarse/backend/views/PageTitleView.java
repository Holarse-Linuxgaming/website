package de.holarse.backend.views;

public interface PageTitleView extends View {
    
    default String getPageTitle() {
        return "Eure deutschsprachige Linuxspiele-Community";
    }
    
    String getUrl();
    String getEditUrl();
    
    String getFormattedContent();
    String getPlainContent();
}
