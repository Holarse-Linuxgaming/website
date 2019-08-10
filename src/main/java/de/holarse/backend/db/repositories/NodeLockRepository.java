package de.holarse.backend.db.repositories;

import de.holarse.backend.db.NodeLock;
import de.holarse.backend.db.User;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface NodeLockRepository extends CrudRepository<NodeLock, Long> {

    /**
     * Das älteste Lock falls vorhanden
     * @param nodeId
     * @param date
     * @param user
     * @return 
     */
    Optional<NodeLock> findFirstByNodeIdAndLockUntilAfterAndUserNot(final long nodeId, final OffsetDateTime date, final User user);
    
    @Modifying
    void deleteByNodeId(final long nodeId);
    
}
