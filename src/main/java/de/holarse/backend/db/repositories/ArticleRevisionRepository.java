package de.holarse.backend.db.repositories;

import de.holarse.backend.db.ArticleRevision;
import de.holarse.backend.db.datasets.CurrentArticle;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author comrad
 */
@Repository
public interface ArticleRevisionRepository extends JpaRepository<ArticleRevision, Integer>, RevisionAwareRepository  {    
//    
//    @Query("FROM ArticleRevision ar " + 
//           "INNER JOIN NodeStatus ns ON ns.nodeId = ar.nodeId " +
//           "INNER JOIN Article a ON a.versionId = ar.id " +
//           "WHERE NOT ns.deleted and a.nodeId = (SELECT ns.nodeId from NodeSlug ns WHERE ns.name = :slug)")
//    Optional<ArticleRevision> findCurrentBySlug(@Param("slug") final String slug);
//    
//    @Query("FROM ArticleRevision ar " + 
//           "INNER JOIN Article a ON a.nodeId = ar.nodeId " +
//           "WHERE ar.nodeId = :nodeId")
//    Optional<ArticleRevision> findCurrentByNodeId(@Param("nodeId") final int nodeId);
}
