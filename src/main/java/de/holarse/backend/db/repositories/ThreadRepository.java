package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Forum;
import de.holarse.backend.db.ForumThread;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ThreadRepository extends CrudRepository<ForumThread, Long> {
    
    Optional<ForumThread> findBySlugAndForum(final String slug, final Forum forum);    
    
}
