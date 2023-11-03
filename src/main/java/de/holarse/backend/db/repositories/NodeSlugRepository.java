package de.holarse.backend.db.repositories;

import de.holarse.backend.db.NodeSlug;
import de.holarse.backend.types.NodeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeSlugRepository extends JpaRepository<NodeSlug, Integer> {
    
    @Query("FROM NodeSlug ns WHERE ns.nodeId = :nodeId ORDER BY ns.id DESC LIMIT 1")
    Optional<NodeSlug> findByNodeId(@Param("nodeId") final int nodeId);
    
    boolean existsByNameAndSlugContext(final String name, final NodeType slugContext);
    
}
