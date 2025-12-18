package de.holarse.backend.db.repositories;

import de.holarse.backend.db.AttachmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author comrad
 */
@Repository
public interface AttachmentTypeRepository extends JpaRepository<AttachmentType, Integer> {
    
    AttachmentType findByCode(String code);            
    
}
