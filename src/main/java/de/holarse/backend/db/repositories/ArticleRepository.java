package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.datasets.CurrentArticle;
import java.util.List;
import java.util.Optional;
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
public interface ArticleRepository extends JpaRepository<Article, Integer>, NodeAwareRepository {
    
    @Query(value = "SELECT a.nodeId as nodeId, ar.id as revisionId, ar.title1 as title1, sl.name as slug from Article a " +
                   "INNER JOIN a.articleRevision as ar " + 
                   "INNER JOIN NodeStatus ns on ns.nodeId = a.nodeId " +
                   "INNER JOIN NodeSlug sl on sl.nodeId = a.nodeId " +
                   "WHERE ns.published and not ns.deleted and sl.id = (SELECT max(_sl.id) FROM NodeSlug _sl where _sl.nodeId = a.nodeId)")
    List<CurrentArticle> listCurrentArticles(final Pageable pageable);
    
    @Query(value = "from Article a " +
                   "JOIN FETCH a.articleRevision " + 
                   "LEFT JOIN FETCH a.tags " +
                   "WHERE a.nodeId = :nodeId")
    Optional<Article> findByNodeId(@Param("nodeId") final int nodeId);
    
    @Query(value = "FROM Article a " + 
                   "JOIN FETCH a.articleRevision " + 
                   "JOIN FETCH a.nodeStatus as ns " +        
                   "JOIN a.nodeSlugs as sl " +
                   "LEFT JOIN FETCH a.tags " +            
                   "WHERE NOT ns.deleted " + 
                   "AND sl.name = :slug")
    Optional<Article> findBySlug(@Param("slug") final String slug);
    
}
