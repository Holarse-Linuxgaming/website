/*
 * Copyright (C) 2023 comrad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.holarse.backend.db;

import de.holarse.backend.types.NodeType;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import java.time.OffsetDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

/**
 * Allgemeine Writelock-Tabelle für alle Arten von geteilten, speicherbaren Daten.
 * @author comrad
 */
@Table(name = "entity_writelocks")
@Entity
public class EntityWriteLock extends Base  {

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    @Type(PostgreSQLEnumType.class)
    @Column(name = "entity", columnDefinition = "node_type")
    private NodeType entity;    
   
    @Column(name="row_id")
    private Integer rowId;
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false, referencedColumnName = "id")
    private User writeLockUser;    

    @Column(name = "write_lock_updated", columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")
    private OffsetDateTime writeLockUpdated;        

    public NodeType getEntity() {
        return entity;
    }

    public void setEntity(NodeType entity) {
        this.entity = entity;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public User getWriteLockUser() {
        return writeLockUser;
    }

    public void setWriteLockUser(User writeLockUser) {
        this.writeLockUser = writeLockUser;
    }

    public OffsetDateTime getWriteLockUpdated() {
        return writeLockUpdated;
    }

    public void setWriteLockUpdated(OffsetDateTime writeLockUpdated) {
        this.writeLockUpdated = writeLockUpdated;
    }
    
}
