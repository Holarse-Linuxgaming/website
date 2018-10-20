package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Message;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends CrudRepository<Message, Long>{
    
    @Query("SELECT m FROM Message m WHERE m.author = :user")
    List<Message> allMyMessages(@Param("user") final String user);
    
    Optional<Message> findByUuid(final String uuid);
    
}
