package de.holarse.exceptions;

import de.holarse.backend.db.NodeLock;

public class NodeLockException extends RuntimeException {

    private final NodeLock nodeLock;

    public NodeLockException(final NodeLock nodeLock) {
        this.nodeLock = nodeLock;
    }

    public NodeLock getNodeLock() {
        return nodeLock;
    }

}
