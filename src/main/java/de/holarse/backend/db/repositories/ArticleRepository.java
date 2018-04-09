package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Article;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends CrudRepository<Article, Long>, SluggableRepository<Article>  {
    
    Article findByTitle(final String title);

    @Query("select a from Article a inner join a.tags t where t.name in :tagnames")
    List<Article> findByTags(@Param("tagnames") List<String> tagnames);
    
}
