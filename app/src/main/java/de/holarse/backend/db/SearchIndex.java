package de.holarse.backend.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Als Abbildung über Spring Data Repository SearchRepository
 * @author comrad
 */
@Table(name = "mv_searchindex")
@Entity
public class SearchIndex {
    
    @Id
    @Column(name = "nodeid")
    private Integer nodeId;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }
    
}
