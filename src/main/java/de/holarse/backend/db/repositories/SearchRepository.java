package de.holarse.backend.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.holarse.backend.db.SearchIndex;
import de.holarse.backend.db.datasets.SearchResultView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface SearchRepository extends JpaRepository<SearchIndex, Integer> {
    
    @Query(value = "select nodeid as nodeid, ptitle as title, purl as url from mv_searchindex " +
                   "where document @@ websearch_to_tsquery('german', :query) " +
                   "order by ts_rank(document, websearch_to_tsquery('german', 'gesabbel')) desc ", nativeQuery = true)
    Page<SearchResultView> search(@Param("query") final String query, final Pageable pageable);
    
    @Query(value = "select nodeid as nodeid, ptitle as title, purl as url from mv_searchindex " +
                   "where string_to_array(tags, '~') @> string_to_array(:tags, '~')", nativeQuery = true)            
    Page<SearchResultView> searchTags(@Param("tags") final String tags, final Pageable pageable);

    @Query(value = "select * from ( " + 
                   "select nodeid as nodeid, ptitle as title, purl as url from mv_searchindex " +
                   "where string_to_array(tags, '~') @> string_to_array(:tags, '~') " +
                   "and document @@ websearch_to_tsquery('german', :filter) " +
                   "order by ts_rank(document, websearch_to_tsquery('german', :filter)) desc) as x ", nativeQuery = true)
    Page<SearchResultView> searchTags(@Param("tags") final String tags, @Param("filter") final String filter, final Pageable pageable);    
    
    @Modifying
    @Query(value = "refresh materialized view mv_searchindex", nativeQuery = true)
    void refreshIndex();
    
}
