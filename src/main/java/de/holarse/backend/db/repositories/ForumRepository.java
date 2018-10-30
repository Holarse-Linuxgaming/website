package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Forum;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ForumRepository extends CrudRepository<Forum, Long> {
 
    List<Forum> findAllByOrderByWeight();
    
    Optional<Forum> findBySlug(final String slug);
    
}
