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

@Table(name = "articles")
@Entity
public class Article extends Base {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "nodeid")
    private int nodeId;
    
    @OneToOne
    @JoinColumn(name = "revisionid", referencedColumnName = "id")
    private ArticleRevision articleRevision;
    
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
    @JoinColumn(name="nodeid", insertable=false, nullable=false, updatable = false, referencedColumnName = "nodeid")    
    private Set<NodeSlug> nodeSlugs = new HashSet<>();
    
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

    public ArticleRevision getArticleRevision() {
        return articleRevision;
    }

    public void setArticleRevision(ArticleRevision articleRevision) {
        this.articleRevision = articleRevision;
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

}
