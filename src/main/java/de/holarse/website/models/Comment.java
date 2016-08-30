package de.holarse.website.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "comments")
public class Comment extends EntityBase {
    
    @Column(length = 8192)
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
