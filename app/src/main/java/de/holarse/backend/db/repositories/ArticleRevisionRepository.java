package de.holarse.backend.db.repositories;

import de.holarse.backend.db.ArticleRevision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author comrad
 */
@Repository
public interface ArticleRevisionRepository extends JpaRepository<ArticleRevision, Integer>, RevisionAwareRepository  {    

    @Query("""
        select ar from ArticleRevision ar 
        where ar.nodeId = :nodeId""")
    Page<ArticleRevision> findHistory(@Param("nodeId") Integer nodeId, Pageable pageable);

    @Query("""
        select ar from ArticleRevision ar 
        where ar.nodeId = :nodeId 
        and ar.revision = :revisionId""")
    Optional<ArticleRevision> findByRevisionId(@Param("nodeId") Integer nodeId, @Param("revisionId") Integer revisionId);

}
