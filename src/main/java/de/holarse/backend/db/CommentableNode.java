package de.holarse.backend.db;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public class CommentableNode extends Node {
    
    /**
     * Kann kommentiert werden
     */
    @Column(nullable = false, columnDefinition = "boolean default true")    
    private Boolean commentable;    
    
    @OneToMany
    @JoinColumn(name = "node_id")
    private List<Comment> comments = new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Boolean getCommentable() {
        return commentable;
    }

    public void setCommentable(Boolean commentable) {
        this.commentable = commentable;
    }
}
