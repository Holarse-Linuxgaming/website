package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Article;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long>, SluggableRepository<Article>  {
    
    Article findByTitle(final String title);
    
    /**
     * Sucht einen Artikel anhand seiner Drupal-Id
     * @param oldid
     * @return 
     */
    Optional<Article> findByOldId(final Long oldId);

//    @Query(value = "select a.* from articles a where a.id in ( " +
//           "select a.id from articles a " +
//           "inner join articles_tags at on at.article_id = a.id " +
//           "inner join tags t on at.tags_id = t.id " +
//           "where t.name in :tagnames " +
//           "group by a.id " +
//           "having count(a.id) = :count)", nativeQuery = true)
//    List<Article> findByTags(@Param("tagnames") Collection<String> tagnames, @Param("count") int count);
    
}
