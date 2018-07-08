package de.holarse.backend.db.repositories;

import de.holarse.backend.db.News;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long>, SluggableRepository<News> {
 
    @Query("FROM News")
    List<News> findAllNodes();
    
}
