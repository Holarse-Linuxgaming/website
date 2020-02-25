package de.holarse.backend.views;

public abstract class AbstractLinkView implements LinkView {
 
    protected String url     = "#";
    protected String editUrl = "#";
    
    @Override
    public String getUrl() {
        return url;
    }
    
    @Override
    public void setUrl(final String url) {
        this.url = url;
    }
    
    @Override
    public String getEditUrl() {
        return editUrl;
    }  
    
    @Override
    public void setEditUrl(final String editUrl) {
        this.editUrl = editUrl;
    }
    
}
