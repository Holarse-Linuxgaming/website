package de.holarse.backend.db.repositories;

import org.springframework.data.jpa.repository.Query;

public interface SequenceBasedRepository {

    @Query(value = "SELECT nextval('node_sequence')")
    int nextNodeId();
    
    @Query(value = "SELECT nextval('revision_sequence')")
    int nextRevision();    
}

