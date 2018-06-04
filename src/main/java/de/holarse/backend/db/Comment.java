package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="comments")
@Entity
public class Comment extends Node implements Searchable {
    
    @Column(name = "node_id")
    private Long nodeId;
    
    @Enumerated(EnumType.STRING)
    private NodeType nodeType;
   
    @ManyToOne
    private User author;    
    
    @Transient
    private String url;    
    
    @Override
    public String getUrl() {
        return url;
    }

    public NodeType getNodeType() {
        return nodeType;
    }
    
    @PostLoad
    private void nodePostLoad() {
        this.url = nodeType.getUrlPrefix() + "" + nodeId;
    }      

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }
    
    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(User author) {
        this.author = author;
    }
    
    @Override
    public String getIndex() {
        return "comments";
    }
    
    @Override
    public String getTitle() {
        return null;
    }

    
}
