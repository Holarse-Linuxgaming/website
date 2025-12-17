package de.holarse.backend.db.repositories;

import org.springframework.data.jpa.repository.Query;

public interface RevisionAwareRepository {
 
    @Query("SELECT nextval('revision_sequence')")
    int nextRevision();     
    
}
