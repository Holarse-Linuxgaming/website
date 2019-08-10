package de.holarse.backend.db;

public interface NodeState {

    public void setArchived(Boolean archived);
    public Boolean getArchived();

    public void setLocked(Boolean locked);
    public Boolean getLocked();

    public void setCommentable(Boolean commentable);
    public Boolean getCommentable();

    public void setPublished(Boolean published);
    public Boolean getPublished();
    
}