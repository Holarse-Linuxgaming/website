package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Tag;
import de.holarse.backend.view.TagView;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
   
    @Query("FROM Tag t " + 
           "INNER JOIN NodeTag nt on nt.tagId = t.id " +
           "INNER JOIN TagGroup tg on tg.id = t.tagGroup.id " +
           "WHERE nt.nodeId = :nodeId")
    List<TagView> findByNodeId(@Param("nodeId") final int nodeId, final Sort sort);
    
}
