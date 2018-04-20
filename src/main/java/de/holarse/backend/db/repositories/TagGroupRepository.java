package de.holarse.backend.db.repositories;

import de.holarse.backend.db.TagGroup;
import de.holarse.backend.db.TagGroupType;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface TagGroupRepository extends CrudRepository<TagGroup, Long> {
    
    List<TagGroup> findByName(final TagGroupType name);
    
}
