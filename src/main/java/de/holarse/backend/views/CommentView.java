package de.holarse.backend.views;

import de.holarse.backend.db.Comment;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CommentView {

    private Long id;
    private Long nodeId;
    private String author;
    private String content;
    private String created; 

    public CommentView() {
    }

    public CommentView(final Comment comment) {
        this.id = comment.getId();
        this.nodeId = comment.getNodeId();
        this.author = comment.getAuthor().getLogin();
        this.content = comment.getContent();
        this.created = comment.getCreated().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
    
}
