package de.holarse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NodeNotVisibleException extends RuntimeException {

    private Long nodeId;
    
    public NodeNotVisibleException(final String msg, final Long nodeId) {
        super(msg);
        this.nodeId = nodeId;
    }

    public NodeNotVisibleException(final Long nodeId) {
        this.nodeId = nodeId;
    }
    
    public NodeNotVisibleException(final String msg) {
        super(msg);
    }    

    public Long getNodeId() {
        return nodeId;
    }    
}