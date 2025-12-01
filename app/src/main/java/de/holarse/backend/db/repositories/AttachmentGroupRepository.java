package de.holarse.backend.db.repositories;

import de.holarse.backend.db.AttachmentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AttachmentGroupRepository extends JpaRepository<AttachmentGroup, Integer> {

    AttachmentGroup findByCode(final String code);

}
