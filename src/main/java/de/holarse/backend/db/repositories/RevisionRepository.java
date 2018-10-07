package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Revision;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RevisionRepository extends JpaRepository<Revision, Long> {
    
    @Query("select r from Revision r where nodeId = :nodeId")
    List<Revision> findByNodeId(@Param("nodeId") Long nodeId);
    
    @Query(value = "select nextval('revision_sequence')", nativeQuery = true)
    Long nextRevision();
}
