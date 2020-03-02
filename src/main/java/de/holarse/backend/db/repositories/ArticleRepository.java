package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository
        extends JpaRepository<Article, Long>,
                SluggableBranchableRepository<Article>,
                OldIdRepository<Article>,
                PublicNodeRepository<Article> {

    Article findByTitle(final String title);
}
