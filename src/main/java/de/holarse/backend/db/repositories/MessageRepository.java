package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long>{
    
}
