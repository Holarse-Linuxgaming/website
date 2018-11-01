package de.holarse.backend.db.repositories;

import de.holarse.backend.db.VideoNews;
import org.springframework.data.repository.CrudRepository;

public interface VideoNewsRepository extends CrudRepository<VideoNews, Long> {
    
}
