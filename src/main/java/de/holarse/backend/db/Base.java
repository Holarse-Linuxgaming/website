package de.holarse.backend.db;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Die Basis-Klasse für alle Datenbank-Entitäten. Sie enthält die ID und ein
 * Erstell- sowie Aktualisierungsdatum.
 * @author comrad
 */
@MappedSuperclass
@DynamicUpdate
public abstract class Base implements Serializable {
    
    @Id
    @GeneratedValue    
    private Long id;
    
    @Column(insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL default CURRENT_TIMESTAMP")    
    private OffsetDateTime created;
    
    @Column(insertable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")    
    private OffsetDateTime updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
