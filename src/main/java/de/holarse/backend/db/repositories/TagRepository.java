package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends CrudRepository<Tag, Long> {
    
    Optional<Tag> findByNameIgnoreCase(String name);
    
    @Query("FROM Tag t WHERE t.tagGroup.name = :tagGroupName order by t.useCount desc, t.name")
    List<Tag> findByTagGroup(@Param("tagGroupName") final String tagGroupName);
    
    @Query("FROM Tag t WHERE t.tagGroup is null order by t.useCount desc, t.name")
    List<Tag> findFreeTags();
    
    @Query(value = "SELECT t.* FORM tags t INNER JOIN articles_tags at ON at.tags_id =  t.id WHERE at.article_id = :articleId", nativeQuery = true)
    List<Tag> findArticleTags(@Param("articleId") final long articleId);
    
}
