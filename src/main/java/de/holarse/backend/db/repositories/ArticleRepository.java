package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Article;
import de.holarse.web.seo.SiteMapNode;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository
        extends JpaRepository<Article, Long>,
                SluggableBranchableRepository<Article>,
                OldIdRepository<Article> {

    Article findByTitle(final String title);

    @Query(
            value =
                    "SELECT new de.holarse.web.seo.SiteMapNode(article.id, article.slug, article.created, article.updated, 'article')"
                            + " FROM de.holarse.backend.db.Article article")
    List<SiteMapNode> findAllForSiteMap();

    //    @Query(value = "select a.* from articles a where a.id in ( " +
    //           "select a.id from articles a " +
    //           "inner join articles_tags at on at.article_id = a.id " +
    //           "inner join tags t on at.tags_id = t.id " +
    //           "where t.name in :tagnames " +
    //           "group by a.id " +
    //           "having count(a.id) = :count)", nativeQuery = true)
    //    List<Article> findByTags(@Param("tagnames") Collection<String> tagnames, @Param("count")
    // int count);
}
