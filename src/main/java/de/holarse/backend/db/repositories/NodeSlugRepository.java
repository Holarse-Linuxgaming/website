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
    
    @Query("from NodeSlug ns where ns.nodeId = :nodeId order by ns.id desc limit 1")
    Optional<NodeSlug> findByNodeId(@Param("nodeId") final int nodeId);
    
    boolean existsByNameAndSlugContext(final String name, final NodeType slugContext);
    
    @Query("from NodeSlug ns where nodeId = :nodeId order by ns.created desc limit 1")
    Optional<NodeSlug> findMainSlug(@Param("nodeId") final Integer nodeId);
    
}
