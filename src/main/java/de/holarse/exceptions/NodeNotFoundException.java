package de.holarse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
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
