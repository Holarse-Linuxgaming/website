package de.holarse.web.videonews;

public class YoutubeVideo {

    /**
     * Die ursprüngliche URL
     */
    private String url;
    
    /**
     * Die YoutubeID
     */
    private String youtubeId;
    
    /**
     * Die URL auf Youtube
     */
    private String youtubeUrl;
    
    /**
     * Die verwendete Thumbnail-URL
     */
    private String thumbnail;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
    
}
