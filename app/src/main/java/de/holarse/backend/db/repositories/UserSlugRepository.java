package de.holarse.backend.db.repositories;

import de.holarse.backend.db.UserSlug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSlugRepository extends JpaRepository<UserSlug, Integer> {

    @Query(value = "select count(1) from user_slugs us where us.name = :name", nativeQuery = true)
    int isSlugUsed(@Param("name") final String name);
    
}
