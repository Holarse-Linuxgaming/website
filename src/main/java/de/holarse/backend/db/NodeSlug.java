package de.holarse.backend.db;

import de.holarse.backend.types.NodeType;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

@Table(name = "node_slugs")
@Entity
public class NodeSlug extends TimestampedBase {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "nodeid", nullable = false)
    private Integer nodeId;
    
    @Column(length = 255)
    private String name;
    
    @Column
    private boolean main;
    
    @Enumerated(EnumType.STRING)
    @Type(PostgreSQLEnumType.class)
    @Column(columnDefinition = "node_type", name = "slug_context")
    private NodeType slugContext;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeType getSlugContext() {
        return slugContext;
    }

    public void setSlugContext(NodeType slugContext) {
        this.slugContext = slugContext;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

}
