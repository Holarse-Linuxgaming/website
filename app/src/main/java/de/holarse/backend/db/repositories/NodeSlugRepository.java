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
    
    @Query("select ns from NodeSlug ns where ns.nodeId = :nodeId order by ns.id desc limit 1")
    Optional<NodeSlug> findByNodeId(@Param("nodeId") int nodeId);
    
    boolean existsByNameAndSlugContext(String name, NodeType slugContext);
    
    @Query("select ns from NodeSlug ns where ns.nodeId = :nodeId and ns.slugContext = :context order by ns.created desc limit 1")
    Optional<NodeSlug> findMainSlug(@Param("nodeId") Integer nodeId, @Param("context") NodeType nodeType);
    
}
