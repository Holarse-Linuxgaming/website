package de.holarse.backend.db;

public interface Frontpagable {

    Long getNodeId();

    String getTeaser();

    String getTitle();

    String getUrl();
    
    NodeType getNodeType();
    
}
