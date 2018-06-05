package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Slug;
import org.springframework.data.repository.CrudRepository;

public interface SlugRepository extends CrudRepository<Slug, Long> {
    
        int countBySlug(String slug);
        Slug findBySlug(String slug);
    
}
