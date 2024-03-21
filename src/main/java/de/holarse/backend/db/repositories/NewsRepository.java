package de.holarse.backend.db.repositories;

import de.holarse.backend.db.News;
import de.holarse.backend.view.FrontpageItemView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer>, NodeAwareRepository {

    @Query(value = "SELECT nr.nodeId, sl.name as slug, 'news' as nodeType, nr.title as title, nr.teaser FROM News n "
            + "JOIN n.newsRevision nr "
            + "JOIN n.nodeStatus as ns "
            + "JOIN n.nodeSlugz as sl "
            + "WHERE ns.published and NOT ns.deleted and sl.id = (SELECT max(_sl.id) FROM NodeSlug _sl where _sl.nodeId = n.nodeId)")
    List<FrontpageItemView> findFrontpageItems(final Pageable pageable);
}
