package de.holarse.backend.db.repositories;

import de.holarse.backend.db.AttachmentType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author comrad
 */
public interface AttachmentTypeRepository extends JpaRepository<AttachmentType, Integer> {
    
    AttachmentType findByCode(String code);            
    
}
