package de.holarse.backend.db.repositories;

import de.holarse.backend.db.DrückblickEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DrückblickRepository extends JpaRepository<DrückblickEntry, Integer>{
 
    @Query("select de from DrückblickEntry de where not de.done order by de.created")
    List<DrückblickEntry> findUnattended();
    
}
