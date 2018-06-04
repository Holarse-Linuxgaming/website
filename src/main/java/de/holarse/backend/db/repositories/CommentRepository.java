package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Comment;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    
    List<Comment> findAllByOrderByCreated();
    
}
