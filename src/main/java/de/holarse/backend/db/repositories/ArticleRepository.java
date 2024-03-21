package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Article;

import java.util.List;
import java.util.Optional;

import de.holarse.backend.view.FrontpageItemView;
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

    @Query(value = "SELECT nr.nodeId, sl.name as slug, null as nodeType, nr.title1 as title, nr.teaser FROM Article n " +
            "JOIN n.nodeRevision as nr " +
            "JOIN n.nodeStatus as ns " +
            "JOIN n.nodeSlugs as sl " +
            "WHERE ns.published and NOT ns.deleted and sl.id = (SELECT max(_sl.id) FROM NodeSlug _sl where _sl.nodeId = n.nodeId)")
    List<FrontpageItemView> findFrontpageItems(final Pageable pageable);
    
    @Query(value = "from Article a " +
                   "JOIN FETCH a.nodeRevision " + 
                   "LEFT JOIN FETCH a.tags " +
                   "WHERE a.nodeId = :nodeId")
    Optional<Article> findByNodeId(@Param("nodeId") final int nodeId);
    
    @Query(value = "FROM Article a " + 
                   "JOIN FETCH a.nodeRevision nr " + 
                   "JOIN FETCH a.nodeStatus as ns " +        
                   "JOIN a.nodeSlugs as sl " +
                   "LEFT JOIN FETCH a.tags " +            
                   "WHERE ns.published and NOT ns.deleted " +
                   "AND sl.name = :slug")
    Optional<Article> findBySlug(@Param("slug") final String slug);

}
