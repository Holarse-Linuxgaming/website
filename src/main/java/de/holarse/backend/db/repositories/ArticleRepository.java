package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, Long>, SluggableRepository<Article>  {
    
    Article findByTitle(final String title);

}
