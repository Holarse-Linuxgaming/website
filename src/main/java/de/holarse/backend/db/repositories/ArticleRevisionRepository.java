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
public interface ArticleRevisionRepository extends JpaRepository<ArticleRevision, Integer>  {    
    
    @Query("SELECT ar.nodeId as nodeId, ar.revision as revision, ar.title1 as title1, sl.name as slug FROM ArticleRevision ar " + 
           "INNER JOIN Article a ON a.versionId = ar.id " +
            "INNER JOIN NodeStatus ns ON ns.nodeId = ar.nodeId " + 
            "LEFT JOIN NodeSlug sl on sl.nodeId = ar.nodeId " + 
            "WHERE NOT ns.deleted AND ns.published and sl.id = (SELECT max(_ns.id) from NodeSlug _ns where _ns.nodeId = ar.nodeId)")
    Page<CurrentArticle> findAllCurrent(final Pageable pageable);
    
    @Query("FROM ArticleRevision ar " + 
           "INNER JOIN NodeSlug sl ON sl.nodeId = ar.nodeId " +
           "INNER JOIN NodeStatus ns ON ns.nodeId = ar.nodeId " +
           "INNER JOIN Article a ON a.nodeId = ar.nodeId " +
           "WHERE NOT ns.deleted and sl.name = :slug")
    Optional<ArticleRevision> findBySlug(@Param("slug") final String slug);
}
