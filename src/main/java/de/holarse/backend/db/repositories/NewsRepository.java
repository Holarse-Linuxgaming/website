package de.holarse.backend.db.repositories;

import de.holarse.backend.db.News;
import de.holarse.backend.db.NewsRevision;
import de.holarse.backend.db.datasets.CurrentArticle;
import de.holarse.backend.db.datasets.CurrentNews;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NewsRepository extends JpaRepository<News, Integer>, NodeAwareRepository {

    @Query(value = "SELECT e.nodeId as nodeId, rev.id as revisionId, rev.title as title, sl.name as slug from News e " +
            "INNER JOIN e.newsRevision as rev " +
            "INNER JOIN NodeStatus ns on ns.nodeId = e.nodeId " +
            "INNER JOIN NodeSlug sl on sl.nodeId = e.nodeId " +
            "WHERE ns.published and not ns.deleted and sl.id = (SELECT max(_sl.id) FROM NodeSlug _sl where _sl.nodeId = e.nodeId)")
    List<CurrentNews> listCurrent(final Pageable pageable);
}
