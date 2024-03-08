package de.holarse.backend.db.repositories;

import de.holarse.backend.db.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer>, NodeAwareRepository {
}
