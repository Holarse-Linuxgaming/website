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
import de.holarse.backend.types.NodeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author comrad
 */
public interface EntityWriteLockRepository extends JpaRepository<EntityWriteLock, Integer> {
    
    @Query(value="delete from entity_writelocks ew where ew.entity = :entity and ew.row_id = :rowId and ew.user_id = :userId", nativeQuery = true)
    void unlock(@Param("rowId") final Integer rowId, @Param("entity") final NodeType nodeType, @Param("userId") Integer userId);
    
    @Query(value="delete from entity_writelocks ew where ew.entity = :entity and ew.row_id = :rowId", nativeQuery = true)
    void unlockAll(@Param("rowId") final Integer rowId, @Param("entity") final NodeType nodeType);
    
    @Query(value="SELECT 1 from entity_writelocks ew where ew.entity = :entity and ew.row_id = :rowId", nativeQuery = true)
    boolean existsLock(@Param("rowId") final Integer rowId, @Param("entity") final NodeType nodeType);
    
    @Query(value="SELECT ew.* from entity_writelocks ew where ew.entity = :entity", nativeQuery = true)
    List<EntityWriteLock> findAllByType(@Param("entity") final NodeType entity);
    
}
