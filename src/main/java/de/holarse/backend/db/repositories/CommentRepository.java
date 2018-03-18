package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    
}
