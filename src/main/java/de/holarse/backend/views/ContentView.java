package de.holarse.backend.views;

/**
 * Interface für alle Nodes mit renderbarem Content
 */
public interface ContentView extends PageTitleView {

    String getContent();
    String getFormattedContent();
    String getPlainContent();
    String getTeaser();    

}