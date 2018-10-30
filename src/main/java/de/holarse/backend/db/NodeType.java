package de.holarse.backend.db;

public enum NodeType {
 
    ARTICLE("/wiki/"),
    NEWS("/news/"),
    SHORTNEWS("/news/"),
    VIDEO("/news/"),
    STORY("/news/"),
    THREAD("/forum/");
    
    private final String urlPrefix;
    
    NodeType(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }
    
    public String getUrlPrefix() {
        return this.urlPrefix;
    }
    
}
