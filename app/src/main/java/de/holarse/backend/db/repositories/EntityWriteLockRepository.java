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
package de.holarse.backend.db.repositories;

import de.holarse.backend.db.EntityWriteLock;
import de.holarse.backend.db.User;
import de.holarse.backend.types.NodeType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author comrad
 */
@Repository
public interface EntityWriteLockRepository extends JpaRepository<EntityWriteLock, Integer> {

    @Transactional
    @Modifying
    @Query("delete from EntityWriteLock ew where ew.entity = :entity and ew.rowId = :rowId and ew.writeLockUser = :user")
    void unlock(@Param("rowId") final Integer rowId, @Param("entity") final NodeType nodeType, @Param("user") User user);

    @Transactional
    @Modifying
    @Query("delete from EntityWriteLock ew where ew.entity = :entity and ew.rowId = :rowId")
    void unlockAll(@Param("rowId") final Integer rowId, @Param("entity") final NodeType nodeType);
    
    @Query("SELECT case when count(1) > 0 then true else false end from EntityWriteLock ewl where ewl.entity = :entity and ewl.rowId = :rowId")
    boolean existsLock(@Param("rowId") final Integer rowId, @Param("entity") final NodeType nodeType);
    
    @Query("FROM EntityWriteLock ewl where ewl.entity = :entity")
    List<EntityWriteLock> findAllByType(@Param("entity") final NodeType entity);

    @Query("FROM EntityWriteLock ewl where ewl.rowId = :rowId and ewl.writeLockUpdated >= :lockAge")
    Optional<EntityWriteLock> findByLockId(@Param("rowId") final Integer rowId, @Param("lockAge") final OffsetDateTime lockAge);

}
