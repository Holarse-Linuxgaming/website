package de.holarse.backend.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.holarse.backend.db.SearchIndex;
import de.holarse.backend.db.datasets.SearchResultView;

@Repository
public interface SearchRepository extends JpaRepository<SearchIndex, Integer> {
    
    @Query(value = "select nodeid as nodeid, ptitle as title, purl as url from mv_searchindex " +
                   "where document @@ websearch_to_tsquery('german', :query) " + 
                   "order by ts_rank(document, websearch_to_tsquery('german', :query)) desc", nativeQuery = true)
    List<SearchResultView> search(@Param("query") final String query);
    
    @Query(value = "select nodeid as nodeid, ptitle as title, purl as url from mv_searchindex " +
                   "where string_to_array(tags, ';') @> string_to_array(:tags, ';')", nativeQuery = true)
    List<SearchResultView> searchTags(@Param("tags") String tags);

    @Modifying
    @Query(value = "refresh materialized view mv_searchindex", nativeQuery = true)
    void refreshIndex();
    
}
