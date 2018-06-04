package de.holarse.backend.db;

public enum NodeType {
 
    ARTICLE("/wiki/"),
    NEWS("/news/"),
    SHORTNEWS("/news/"),
    VIDEO("/news/"),
    STORY("/news/");
    
    private final String urlPrefix;
    
    NodeType(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }
    
    public String getUrlPrefix() {
        return this.urlPrefix;
    }
    
}
