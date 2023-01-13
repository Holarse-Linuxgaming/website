package de.holarse.backend.db.repositories;

import de.holarse.backend.db.DrückblickEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrückblickRepository extends JpaRepository<DrückblickEntry, Long>{
    
}
