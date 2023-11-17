package de.holarse.backend.db.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.holarse.backend.db.SearchIndex;
import de.holarse.backend.db.datasets.SearchResultView;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface SearchRepository extends JpaRepository<SearchIndex, Integer> {
    
    @Query(value = "select nodeid as nodeid, ptitle as title, purl as url, left(content, 100) || '...' as teaser, doctype, last_update from mv_searchindex " +
                   "where document @@ websearch_to_tsquery('german', :query) and doctype\\:\\:character varying in (:scope)", nativeQuery = true)
    Page<SearchResultView> search(@Param("query") final String query, @Param("scope") final List<String> scope, final Pageable pageable);

    /***
     * Die Tag-Delimiter müssen @@@ sein, weil das Semikolon von Pagable-Ersetzungsmuster fälschlicherweise als Ende erkannt wird
     * @param tags
     * @param scope
     * @param pageable
     * @return 
     */
    @Query(value = "select nodeid as nodeid, ptitle as title, purl as url, left(content, 100) || '...' as teaser, doctype, last_update from mv_searchindex " +
                   "where string_to_array(tags, '@@@') @> string_to_array(:tags, '@@@') and doctype\\:\\:character varying in (:scope)", nativeQuery = true)            
    Page<SearchResultView> searchTags(@Param("tags") final String tags, @Param("scope") final List<String> scope, final Pageable pageable);

    @Query(value = "select nodeid as nodeid, ptitle as title, purl as url, left(content, 100) || '...' as teaser, doctype, last_update from mv_searchindex " +
                   "where string_to_array(tags, '@@@') @> string_to_array(:tags, '@@@') " +
                   "and document @@ websearch_to_tsquery('german', :query) " + 
                   "and doctype\\:\\:character varying in (:scope)", nativeQuery = true)
    Page<SearchResultView> searchTags(@Param("tags") final String tags, @Param("query") final String query, @Param("scope") final List<String> scope, final Pageable pageable);    
    
    @Modifying
    @Query(value = "refresh materialized view mv_searchindex", nativeQuery = true)
    void refreshIndex();
    
}
