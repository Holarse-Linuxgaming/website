package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Tag;
import de.holarse.backend.db.TagGroupType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends CrudRepository<Tag, Long> {
    
    Optional<Tag> findByNameIgnoreCase(String name);
    
    @Query("FROM Tag t WHERE t.tagGroup.name = :tagGroupName order by t.name")
    List<Tag> findByTagGroup(@Param("tagGroupName") final TagGroupType tagGroupType);
    
    @Query("FROM Tag t WHERE t.tagGroup is null order by name")
    List<Tag> findFreeTags();
    
}
