package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.ArticleRevision;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author comrad
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, SequenceBasedRepository {
}
