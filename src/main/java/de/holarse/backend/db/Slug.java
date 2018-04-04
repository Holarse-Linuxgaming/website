package de.holarse.backend.db;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "slugs")
@Entity
public class Slug extends Base {

    private String nodeType;
    private Long nodeId;
    private String link;

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
}
