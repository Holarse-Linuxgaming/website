package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author comrad
 */
public interface ArticleRepository extends JpaRepository<Article, Long>, SequenceBasedRepository {
    
}
