package de.holarse.backend.view;

import de.holarse.backend.db.Attachment;

public class YoutubeView extends AttachmentView {

    final static transient String YOUTUBE_NOCOOKIE_URL="https://www.youtube-nocookie.com/embed/%s?origin=https://holarse.de";

    private String url;

    public YoutubeView() {
        super();
    }

    public YoutubeView(final AttachmentView av) {
        super();
        setId(av.getId());
        setData(av.getData());
        setWeight(av.getWeight());
        setDescription(av.getDescription());
        setMarkAsDeleted(av.isMarkAsDeleted());
    }

    public static YoutubeView of(final Attachment attachment) {
        final YoutubeView view = new YoutubeView(AttachmentView.of(attachment));
        view.setUrl(String.format(YOUTUBE_NOCOOKIE_URL, attachment.getData()));
        return view;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
