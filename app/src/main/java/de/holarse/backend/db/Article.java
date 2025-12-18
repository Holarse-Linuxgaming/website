package de.holarse.backend.db;

import de.holarse.backend.types.NodeType;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Table(name = "articles")
@Entity
public class Article extends Base implements Node, LockableEntity {

    private static final long serialVersionUID = 2L;
    
    @Column(name = "nodeid")
    private int nodeId;
    
    @OneToOne
    @JoinColumn(name = "revisionid", referencedColumnName = "id")
    private ArticleRevision nodeRevision;
    
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
    
    @OneToMany(cascade = { CascadeType.ALL }, targetEntity=NodeSlug.class)
    @JoinColumn(name="nodeid", insertable=false, nullable=false, updatable = false, referencedColumnName = "nodeid")
    private Set<NodeSlug> nodeSlugs = new HashSet<>();
    
    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override    
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getDrupalId() {
        return drupalId;
    }

    public void setDrupalId(Integer drupalId) {
        this.drupalId = drupalId;
    }

    public ArticleRevision getNodeRevision() {
        return nodeRevision;
    }

    public void setNodeRevision(ArticleRevision nodeRevision) {
        this.nodeRevision = nodeRevision;
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

    public Set<NodeSlug> getNodeSlugs() {
        return nodeSlugs;
    }

    public void setNodeSlugs(Set<NodeSlug> nodeSlugs) {
        this.nodeSlugs = nodeSlugs;
    }

    @Transient
    public NodeType getNodeType() { return NodeType.article; }
    
}
