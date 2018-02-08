/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.backend.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Base implements Serializable {
    
    @Id
    @GeneratedValue    
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date generated;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGenerated() {
        return generated;
    }

    public void setGenerated(Date generated) {
        this.generated = generated;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    
}
