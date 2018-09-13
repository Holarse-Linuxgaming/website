package de.holarse.backend.db.repositories;

import de.holarse.backend.db.TagGroup;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TagGroupRepository extends CrudRepository<TagGroup, Long> {
    
    List<TagGroup> findByName(final String name);

    @Query("from TagGroup tg order by tg.weight desc")
    List<TagGroup> findSortedTagGroups();

}
