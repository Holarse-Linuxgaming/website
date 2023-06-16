package de.holarse.backend.db.repositories;

import de.holarse.backend.db.DrückblickEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrückblickRepository extends JpaRepository<DrückblickEntry, Integer>{
 
    @Query(value = "select de.* from drückblick_entries de where not done order by created", nativeQuery = true)
    public List<DrückblickEntry> findUnattended();
    
}
