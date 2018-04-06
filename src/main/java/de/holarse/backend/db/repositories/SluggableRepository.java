package de.holarse.backend.db.repositories;

public interface SluggableRepository<N> {

    int countBySlug(String slug);
    N findBySlug(String slug);
    
}
