package de.holarse.exceptions;

public class NodeNotFoundException extends RuntimeException {

    private final Long nodeId;
    
    public NodeNotFoundException(final Long nodeId) {
        this.nodeId = nodeId;
    }

    public Long getNodeId() {
        return nodeId;
    }
   
}
