package de.holarse.backend.db.repositories;

import de.holarse.backend.db.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Integer> {

    @Query("from NewsCategory nc where nc.active order by nc.weight desc, nc.name")
    List<NewsCategory> findActiveCategories();

}
