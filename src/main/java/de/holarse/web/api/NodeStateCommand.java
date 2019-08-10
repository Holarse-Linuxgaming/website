package de.holarse.web.api;

import de.holarse.backend.db.NodeState;
import de.holarse.backend.db.NodeType;

/**
 * Transportobjekt für die Statusänderung eines Node. NodeState wird hier nur eingebunden, um die
 * Felder synchron zu halten.
 */
public class NodeStateCommand implements NodeState {

    private Long nodeId;

    private NodeType nodeType;

    /**
     * Node ist archiviert.
     */
    private Boolean archived;

    /**
     * Node ist gesperrt.
     */
    private Boolean locked;

    /**
     * Node kann kommentiert werden.
     */
    private Boolean commentable;

    /**
     * Node ist veröffentlicht.
     */
    private Boolean published;

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getCommentable() {
        return commentable;
    }

    public void setCommentable(Boolean commentable) {
        this.commentable = commentable;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

}