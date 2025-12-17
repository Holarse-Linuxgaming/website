package de.holarse.backend.db.repositories;

import de.holarse.backend.db.News;
import de.holarse.backend.view.FrontpageItemView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.repository.query.Param;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer>, NodeAwareRepository {

    @Query(value = "SELECT nr.nodeId, sl.name as slug, 'news' as nodeType, nr.title as title, nr.teaser, concat('news/', sl.name) as url FROM News n "
            + "JOIN n.nodeRevision nr "
            + "JOIN n.nodeStatus as ns "
            + "JOIN n.nodeSlugz as sl "
            + "WHERE ns.published and NOT ns.deleted and sl.id = (SELECT max(_sl.id) FROM NodeSlug _sl where _sl.nodeId = n.nodeId)")
    Page<FrontpageItemView> findFrontpageItems(final Pageable pageable);
    
    @Query(value = "FROM News n " + 
                   "JOIN FETCH n.nodeRevision nr " + 
                   "JOIN FETCH n.nodeStatus as ns " +        
                   "JOIN n.nodeSlugz as sl " +
                   "LEFT JOIN FETCH n.tags " +            
                   "WHERE ns.published and NOT ns.deleted " +
                   "AND sl.name = :slug")
    Optional<News> findBySlug(@Param("slug") final String slug);    
    
    Optional<News> findByNodeId(final Integer nodeId);
}
