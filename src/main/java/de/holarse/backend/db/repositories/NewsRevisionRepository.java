package de.holarse.backend.db.repositories;

import de.holarse.backend.db.NewsRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRevisionRepository extends JpaRepository<NewsRevision, Integer>, RevisionAwareRepository {
}
