package de.holarse.backend.db;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Table(name = "news")
@Entity
public class News extends Base {

    private static final long serialVersionUID = 2L;

    @Column(name = "nodeid")
    private int nodeId;

    @OneToOne
    @JoinColumn(name = "revisionid", referencedColumnName = "id")
    private NewsRevision newsRevision;

    @Column(name = "drupalid")
    private Integer drupalId;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="nodeid", insertable=false, nullable=false, updatable = false, referencedColumnName = "nodeid")
    private NodeStatus nodeStatus;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "node_tags",
            joinColumns = { @JoinColumn(name = "nodeid", insertable=false, nullable=false, updatable = false, referencedColumnName = "nodeid") },
            inverseJoinColumns = { @JoinColumn(name = "tagid") }
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name="nodeid", referencedColumnName = "nodeid")
    private Set<NodeSlug> slugs = new HashSet<>(); // Muss nodeSlugz heissen, weil nodeSlugs in Article referenziert ist. Siehe Bug https://lists.jboss.org/pipermail/hibernate-issues/2011-January/027658.html

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getDrupalId() {
        return drupalId;
    }

    public void setDrupalId(Integer drupalId) {
        this.drupalId = drupalId;
    }

    public NewsRevision getNewsRevision() {
        return newsRevision;
    }

    public void setNewsRevision(NewsRevision articleRevision) {
        this.newsRevision = newsRevision;
    }

    public NodeStatus getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(NodeStatus nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<NodeSlug> getSlugs() {
        return slugs;
    }

    public void setSlugs(Set<NodeSlug> slugs) {
        this.slugs = slugs;
    }

    @Override
    public String toString() {
        return "News{" +
                "nodeId=" + nodeId +
                ", newsRevision=" + newsRevision +
                ", drupalId=" + drupalId +
                ", nodeStatus=" + nodeStatus +
                ", tags=" + tags +
                ", slugs=" + slugs +
                '}';
    }
}
