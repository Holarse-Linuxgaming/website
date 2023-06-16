package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Job;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobRepository extends JpaRepository<Job, Integer> {
    
    @Query(value = "SELECT j.* FROM jobs j WHERE j.queue = :queue AND j.context = :context AND not j.ignore AND not j.completed and j.tries < 3", nativeQuery = true)
    List<Job> findOpenJobs(@Param("queue") final int queue, @Param("context") final String context);
    
    
}
