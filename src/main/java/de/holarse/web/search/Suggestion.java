package de.holarse.web.search;

public class Suggestion {

    private String url;
    private String thumbnail;
    private String title;
    private String teaser;
    private String category;

    public Suggestion() {
    }
    
    public Suggestion(String url, String thumbnail, String title, String teaser, String category) {
        this.url = url;
        this.thumbnail = thumbnail;
        this.title = title;
        this.teaser = teaser;
        this.category = category;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
