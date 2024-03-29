package de.holarse.backend.db;

import java.time.OffsetDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class TimestampedBase extends Base {

    private static final long serialVersionUID = 1L;
    
    @Column(insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL default CURRENT_TIMESTAMP")    
    private OffsetDateTime created;
    
    @Column(insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")    
    private OffsetDateTime updated;    
 
    public OffsetDateTime getCreated() {
        return created;
    }

    public void setCreated(OffsetDateTime created) {
        this.created = created;
    }

    public OffsetDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(OffsetDateTime updated) {
        this.updated = updated;
    }    
    
}
