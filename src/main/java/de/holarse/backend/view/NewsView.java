package de.holarse.backend.view;

import de.holarse.backend.db.NewsCategory;
import de.holarse.backend.db.NewsRevision;
import de.holarse.backend.db.NodeSlug;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author comrad
 */
public class NewsView {
    
    private String title;
    private String teaser;
    private String content;
    private NewsCategory newsCategory;
    private Integer nodeId;
    
    private String url;
    
    private List<AttachmentView> websiteLinks = new ArrayList<>();
    private List<YoutubeView> youtubeVideos = new ArrayList<>();
    
    public static NewsView of(final NewsRevision newsRevision, final NodeSlug slug) {
        final NewsView nv = new NewsView();
        nv.title = newsRevision.getTitle();
        nv.teaser = newsRevision.getTeaser();
        nv.content = newsRevision.getContent();
        nv.newsCategory = newsRevision.getNewsCategory();
        nv.nodeId = newsRevision.getNodeId();
        
        nv.setUrl(String.format("/news/%s", slug.getName()));
        
        return nv;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NewsCategory getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(NewsCategory newsCategory) {
        this.newsCategory = newsCategory;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<AttachmentView> getWebsiteLinks() {
        return websiteLinks;
    }

    public void setWebsiteLinks(List<AttachmentView> websiteLinks) {
        this.websiteLinks = websiteLinks;
    }

    public List<YoutubeView> getYoutubeVideos() {
        return youtubeVideos;
    }

    public void setYoutubeVideos(List<YoutubeView> youtubeVideos) {
        this.youtubeVideos = youtubeVideos;
    }
    
}
