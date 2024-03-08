package de.holarse.backend.db;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class RevisionedNode extends TimestampedBase {

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="update_userid", referencedColumnName = "id")
    private User author;

    @Column(name = "nodeid", nullable = false)
    private Integer nodeId;

    @Column(length = 255)
    private String changelog;

    @Column(name = "revision", nullable = false)
    private Integer revision;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
