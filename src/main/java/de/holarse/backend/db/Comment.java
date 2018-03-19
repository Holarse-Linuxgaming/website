package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="comments")
@Entity
public class Comment extends Base {
    
    @Column(name = "node_id")
    private Long nodeId;

    @Column(nullable = false, columnDefinition = "boolean default false")    
    private Boolean deleted;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType contentType;
        
    @Column(nullable = false, length = 32768)
    private String content;
    
    @ManyToOne
    private User author;    
    
    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
    
}
