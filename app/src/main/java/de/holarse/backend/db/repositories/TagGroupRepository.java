package de.holarse.backend.db.repositories;

import de.holarse.backend.db.TagGroup;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagGroupRepository extends JpaRepository<TagGroup, Integer> {
    
    @Query("from TagGroup tg join fetch tg.tags t")
    List<TagGroup> findAllTagGroups(final Sort sort);
    
    TagGroup findByCode(final String code);
    
}
