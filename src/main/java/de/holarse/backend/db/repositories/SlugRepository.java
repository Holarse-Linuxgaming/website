package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Slug;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SlugRepository extends CrudRepository<Slug, Long> {
    
        int countBySlug(String slug);
        Slug findBySlug(String slug);

        /**
         * Ermittelt einen Slug anhand des aktualisierten Views
         * @param slug
         * @param nodeType
         * @return
         */
        @Query(value = "SELECT s.id FROM search.mv_slugs s WHERE s.slug = :slug and s.nodeType = :nodeType", nativeQuery = true)
        Optional<Long> findBySlugView(@Param("slug") String slug, @Param("nodeType") String nodeType);
    
}
