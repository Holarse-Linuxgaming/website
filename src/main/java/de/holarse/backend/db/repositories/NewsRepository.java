package de.holarse.backend.db.repositories;

import de.holarse.backend.db.News;
import org.springframework.data.jpa.repository.JpaRepository;


// TODO: LAZY!
public interface NewsRepository
        extends JpaRepository<News, Long>,
                SluggableBranchableRepository<News>,
                OldIdRepository<News>,
                PublicNodeRepository<News> {
}
