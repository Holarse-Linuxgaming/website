package de.holarse.backend.db.repositories;

import de.holarse.backend.db.News;
import de.holarse.web.seo.SiteMapNode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsRepository
        extends JpaRepository<News, Long>,
                SluggableBranchableRepository<News>,
                OldIdRepository<News> {

    @Query(
            value =
                    "SELECT new de.holarse.web.seo.SiteMapNode(n.id, n.slug, n.created, n.updated, 'news')"
                            + " FROM de.holarse.backend.db.News n")
    List<SiteMapNode> findAllForSiteMap();
}
