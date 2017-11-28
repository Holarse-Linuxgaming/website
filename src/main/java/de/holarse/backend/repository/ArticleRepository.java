package de.holarse.backend.repository;

import de.holarse.view.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author comrad
 */
public interface ArticleRepository extends JpaRepository<Article, Long>{
    
}
