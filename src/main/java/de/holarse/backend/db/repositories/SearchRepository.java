package de.holarse.backend.db.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.holarse.backend.db.SearchIndex;
import de.holarse.backend.db.datasets.SearchResultView;
import de.holarse.backend.view.TagRecommendation;
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
    
    /**
     * Ermittelt Tags als Vorschlag in der Tagleiste
     * @param query
     * @return 
     */
    @Query(value = "select ms.wlabel as label, ms.use_count as useCount from mv_suggestions ms where ms.wtype = 'tag' and word @@ websearch_to_tsquery('german', :query) order by use_count", nativeQuery = true)
    List<TagRecommendation> autocompleteTags(@Param("query") final String query);
    
}
