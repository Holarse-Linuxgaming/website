package de.holarse.backend.view;

import de.holarse.backend.db.Attachment;

public class YoutubeView {

    private Integer id;
    private String url;
    private String description;
    private Integer weight;

    public static YoutubeView of(final Attachment attachment) {
        final YoutubeView view = new YoutubeView();
        view.setId(attachment.getId());
        view.setUrl(attachment.getData());
        view.setDescription(attachment.getDescription());
        view.setWeight(attachment.getWeight());

        return view;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
