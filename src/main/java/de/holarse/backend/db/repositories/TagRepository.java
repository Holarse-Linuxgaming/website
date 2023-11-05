package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Tag;
import de.holarse.backend.view.TagView;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    
    boolean existsBySlug(final String slug);
    Optional<Tag> findByName(final String name);
    
}
