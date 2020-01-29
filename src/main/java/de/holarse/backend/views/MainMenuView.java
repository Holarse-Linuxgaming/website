package de.holarse.backend.views;

import java.util.ArrayList;
import java.util.List;

/**
 * View für das Hauptmenü
 * @author comrad
 */
public class MainMenuView {
    
    private final String label;
    private final String url;
    private final String description;
    private final String image;
    private final List<MainMenuView> children = new ArrayList<>();

    public MainMenuView(final String label, final String url) {
        this(label, url, null, null, null);
    }
    
    public MainMenuView(final String label, final String url, final List<MainMenuView> children) {
        this(label, url, null, null, children);
    }
    
    public MainMenuView(final String label, final String url, final String description, final String image, final List<MainMenuView> children) {
        this.label = label;
        this.url = url;
        this.description = description;
        this.image = image;
        
        if (children != null && !children.isEmpty())
            this.children.addAll(children);
    }

    public String getLabel() {
        return label;
    }

    public List<MainMenuView> getChildren() {
        return children;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
    
}
