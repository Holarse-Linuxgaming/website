package de.holarse.backend.db.repositories;

import java.util.Optional;

public interface OldIdRepository<N> {
    
    /**
     * Findet einen Node anhand seiner alten Drupal-ID
     * @param oldId
     * @return 
     */
    Optional<N> findByOldId(Long oldId);
    
}
