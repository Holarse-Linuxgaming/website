package de.holarse.backend.view;

import de.holarse.backend.db.ArticleRevision;
import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.NodeSlug;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleView {

    private int nodeId;
    private String slug;
    
    private String title1;
    private String title2;
    private String title3;
    private String title4;
    private String title5;
    private String title6;
    private String title7;

    private List<String> alternativeTitles = new ArrayList<>();
    
    private String content;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String changelog;

    // Calculated Values
    private transient String url;

    // Tags as editable String
    private String tags;
    // Tags as views for display
    private List<TagView> tagList = new ArrayList<>();
    
    private List<AttachmentView> websiteLinks = new ArrayList<>();
    private List<YoutubeView> youtubeVideos = new ArrayList<>();
    private List<ScreenshotView> screenshotViews = new ArrayList<>();

    private Integer revision;
     
    public static ArticleView of(final ArticleRevision ar, final NodeSlug slug) {
        final ArticleView av = new ArticleView();
        av.setNodeId(ar.getNodeId());
        av.setTitle1(ar.getTitle1());
        av.setTitle2(ar.getTitle2());
        av.setTitle3(ar.getTitle3());
        av.setTitle4(ar.getTitle4());
        av.setTitle5(ar.getTitle5());
        av.setTitle6(ar.getTitle6());
        av.setTitle7(ar.getTitle7());
        av.setContent(ar.getContent());
        av.setCreated(ar.getCreated().toLocalDateTime());
        av.setUpdated(ar.getUpdated().toLocalDateTime());
        av.setRevision(ar.getRevision());
        av.setChangelog(ar.getChangelog());

        if (StringUtils.isNotBlank(ar.getTitle2())) { av.getAlternativeTitles().add(ar.getTitle2()); }
        if (StringUtils.isNotBlank(ar.getTitle3())) { av.getAlternativeTitles().add(ar.getTitle3()); }
        if (StringUtils.isNotBlank(ar.getTitle4())) { av.getAlternativeTitles().add(ar.getTitle4()); }
        if (StringUtils.isNotBlank(ar.getTitle5())) { av.getAlternativeTitles().add(ar.getTitle5()); }
        if (StringUtils.isNotBlank(ar.getTitle6())) { av.getAlternativeTitles().add(ar.getTitle6()); }
        if (StringUtils.isNotBlank(ar.getTitle7())) { av.getAlternativeTitles().add(ar.getTitle7()); }

        av.setUrl(String.format("/wiki/%s", slug.getName()));

        return av;        
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    
    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getTitle4() {
        return title4;
    }

    public void setTitle4(String title4) {
        this.title4 = title4;
    }

    public String getTitle5() {
        return title5;
    }

    public void setTitle5(String title5) {
        this.title5 = title5;
    }

    public String getTitle6() {
        return title6;
    }

    public void setTitle6(String title6) {
        this.title6 = title6;
    }

    public String getTitle7() {
        return title7;
    }

    public void setTitle7(String title7) {
        this.title7 = title7;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<TagView> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagView> tagList) {
        this.tagList = tagList;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<AttachmentView> getWebsiteLinks() {
        return websiteLinks;
    }

    public void setWebsiteLinks(List<AttachmentView> websiteLinks) {
        this.websiteLinks = websiteLinks;
    }

    public List<String> getAlternativeTitles() {
        return alternativeTitles;
    }

    public void setAlternativeTitles(List<String> alternativeTitles) {
        this.alternativeTitles = alternativeTitles;
    }

    public List<YoutubeView> getYoutubeVideos() {
        return youtubeVideos;
    }

    public void setYoutubeVideos(List<YoutubeView> youtubeVideos) {
        this.youtubeVideos = youtubeVideos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public List<ScreenshotView> getScreenshots() {
        return screenshotViews;
    }

    public void setScreenshots(List<ScreenshotView> screenshotViews) {
        this.screenshotViews = screenshotViews;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }
}
