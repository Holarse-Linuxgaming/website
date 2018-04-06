package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(name = "slugs",
        uniqueConstraints = { @UniqueConstraint(columnNames = 
                                           { "nodeType", "slug" }) })
@Entity
public class Slug extends Base {

    @Enumerated(EnumType.STRING)
    private NodeType nodeType;
    private Long nodeId;
    @Column(unique = true)
    private String slug;

    public NodeType getNodeType() {
        return nodeType;
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
    
}
