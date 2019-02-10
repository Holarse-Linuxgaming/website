package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Attachment;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {
 
    List<Attachment> findByNodeId(final Long nodeId);

    @Transactional
    Long deleteByNodeId(final Long nodeId);
}
