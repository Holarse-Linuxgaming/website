package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Nodes, die auch Versionen haben können (Artikel, News, Kommentare)
 * @author comrad
 */
@MappedSuperclass
public abstract class RevisionableNode extends Node {
    
    @Column(columnDefinition = "int default nextval('revision_sequence')", insertable = false) // TODO füllt sich nicht automatisch beim nullen
    private Long revision;    
    
    @Column(length = 1024)
    private String changelog; // Änderung für den akutuellen Knoten

    public Long getRevision() {
        return revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }
    
    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }    
    
}
