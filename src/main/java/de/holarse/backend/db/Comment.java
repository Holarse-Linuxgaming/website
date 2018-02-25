package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name="comments")
public class Comment extends Node {
    
    @Column(name = "node_id")
    private Long nodeId;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }
    
}
