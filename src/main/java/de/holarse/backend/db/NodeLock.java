package de.holarse.backend.db;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "nodelocks")
public class NodeLock extends Base {
    
    private long nodeId;
    @ManyToOne
    private User user;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")    
    private OffsetDateTime lockUntil;    

    public OffsetDateTime getLockUntil() {
        return lockUntil;
    }

    public void setLockUntil(OffsetDateTime lockUntil) {
        this.lockUntil = lockUntil;
    }
    
    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
