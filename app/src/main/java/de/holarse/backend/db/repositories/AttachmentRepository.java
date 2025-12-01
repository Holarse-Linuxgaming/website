package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Attachment;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    
    @Query("from Attachment a " + 
            "join a.attachmentType at " +
            "join at.attachmentGroup ag " +
            "where a.nodeId = :nodeId and ag.code = :code " + 
            "order by a.weight, a.id")
    List<Attachment> findByGroup(@Param("nodeId") Integer nodeId, @Param("code") String code);

    @Query("from Attachment a " +
            "join fetch a.attachmentType at " +
            "join fetch at.attachmentGroup ag " +
            "where a.nodeId = :nodeId")
    List<Attachment> findByNode(@Param("nodeId") Integer nodeId);
}
