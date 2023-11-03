package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Article;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author comrad
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>, NodeAwareRepository {
    
    Optional<Article> findByNodeId(final int nodeId);
    
}
