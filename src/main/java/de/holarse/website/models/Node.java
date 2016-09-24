package de.holarse.website.models;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Node extends EntityBase {

    @Column(columnDefinition = "boolean default true")
    private boolean commentable;
    
    @Column(columnDefinition = "boolean default false")
    private boolean frozen;
    
    @Column(length = 512)
    private String title;
    
    @ManyToMany
    @JoinTable(name = "comments", joinColumns = @JoinColumn(name = "node_id"), inverseJoinColumns = @JoinColumn(name = "node_id"))
    private Set<Comment> comments;    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
    
}
