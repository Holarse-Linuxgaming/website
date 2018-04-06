package de.holarse.exceptions;

public class NodeNotFoundException extends RuntimeException {

    private Long nodeId;
    private String ident;
    
    public NodeNotFoundException(final Long nodeId) {
        this.nodeId = nodeId;
    }
    
    public NodeNotFoundException(final String ident) {
        this.ident = ident;
    }    

    public Long getNodeId() {
        return nodeId;
    }
   
}
