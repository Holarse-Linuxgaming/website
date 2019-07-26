package de.holarse.backend.db;

public enum NodeType {
 
    ARTICLE("/html/"),
    NEWS("/news/"),
    SHORTNEWS("/shortnews/"),
    VIDEO("/videonews/"),
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
