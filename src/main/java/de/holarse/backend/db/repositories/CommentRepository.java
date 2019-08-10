package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Comment;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    
    List<Comment> findAllByOrderByCreated();
    
    @Query("SELECT c FROM Comment c JOIN FETCH c.author a WHERE c.nodeId = (:nodeId) ORDER BY c.created")
    Collection<Comment> findNodeComments(@Param("nodeId") final Long nodeId);
    
}
