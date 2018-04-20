package de.holarse.backend.db.repositories;

import de.holarse.backend.db.ShortNews;
import org.springframework.data.repository.CrudRepository;

public interface ShortNewsRepository extends CrudRepository<ShortNews, Long> {
    
}
