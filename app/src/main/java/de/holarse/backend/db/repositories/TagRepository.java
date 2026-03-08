package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    
    boolean existsBySlug(String slug);
    Optional<Tag> findByName(String name);
    Optional<Tag> findBySlug(String slug);
}
