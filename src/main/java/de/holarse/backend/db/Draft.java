package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Enthält die serialisierten Entwürfe von bestehenden Nodes
 * aber auch von völlig neuen Nodes. Mit der externen
 * Speicherung können Anpassungen jeglicher Nodes vorgenommen werden,
 * ohne diese in den jeweiligen Tabellen speichern zu müssen.
 * 
 * Später wird noch ein Merging in die tatsächliche Datenstruktur nötig sein, um den
 * Entwurf dann letztendlich "live" zu stellen.
 */
@Entity
@Table(name = "drafts")
public class Draft extends Base {

    @ManyToOne
    private User author;

    /**
     * Soft-Link auf die optionale NodeId, falls vorhanden
     */
    private Long nodeId;

    /**
     * Eine mögliche Revisionsnummer der NodeId
     */
    private Long revision;

    /** Enthält die serialisierte Node */
    @Column(length = 8192)
    private String content;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRevision() {
        return revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }

    

}