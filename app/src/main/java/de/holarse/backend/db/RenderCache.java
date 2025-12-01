package de.holarse.backend.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "render_cache")
@Entity
public class RenderCache extends TimestampedBase {

    private static final long serialVersionUID = 1L;

    @Column(name = "node_id")
    private Integer nodeId;
    
    @Column(length = 16384)
    private String content;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
