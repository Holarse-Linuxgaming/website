package de.holarse.backend.db.repositories;

public interface SluggableRepository<N> {

    int countBySlugAndBranch(String slug, String branch);
    N findBySlugAndBranch(String slug, String branch);
    
}
