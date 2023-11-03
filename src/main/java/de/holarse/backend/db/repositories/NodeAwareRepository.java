package de.holarse.backend.db.repositories;

import org.springframework.data.jpa.repository.Query;

public interface NodeAwareRepository {
    
    @Query("SELECT nextval('node_sequence')")
    int nextNodeId();    
    
}
