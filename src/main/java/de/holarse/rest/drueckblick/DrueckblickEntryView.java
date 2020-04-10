package de.holarse.rest.drueckblick;

public class DrueckblickEntryView {

    private String bearer;
    private String link;
    private String message;
    private String createdAt;

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DrueckblickEntryView [bearer=" + bearer + ", link=" + link + ", message=" + message + "]";
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}