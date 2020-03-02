package de.holarse.backend.db.repositories;

import org.springframework.data.util.Streamable;

/**
 * Nur öffentlich sichtbare Nodes
 * @author comrad
 * @param <N> 
 */
public interface PublicNodeRepository<N> {
    
    /**
     * Nur öffentlich sichtbare Nodes
     * @return 
     */
    Streamable<N> findByDeletedFalseAndPublishedTrue();
    
}
