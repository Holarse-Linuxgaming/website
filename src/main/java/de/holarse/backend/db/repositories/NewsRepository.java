package de.holarse.backend.db.repositories;

import de.holarse.backend.db.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {
    
}
