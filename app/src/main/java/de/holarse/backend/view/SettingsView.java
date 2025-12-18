package de.holarse.backend.view;

import de.holarse.backend.db.NodeStatus;

public class SettingsView {

    private boolean archived;
    private boolean commentable;
    private boolean published;
    private boolean deleted;
    
    public static SettingsView of(final NodeStatus nodeStatus) {
        final SettingsView view = new SettingsView();
        view.setArchived(nodeStatus.isArchived());
        view.setCommentable(nodeStatus.isCommentable());
        view.setPublished(nodeStatus.isPublished());        
        view.setDeleted(nodeStatus.isDeleted());
        return view;        
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
}
