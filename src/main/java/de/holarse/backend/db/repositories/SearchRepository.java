package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Node;
import de.holarse.search.SearchResult;
import de.holarse.search.SuggestionResult;
import de.holarse.search.TagSuggestion;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SearchRepository extends JpaRepository<Node, Long> {

    @Modifying
    @Query(value = "REFRESH MATERIALIZED VIEW search.mv_searchindex", nativeQuery = true)
    void update();

    /**
     * Durchsucht den Suchindex anhand von einem Suchbegriff
     * @param query
     * @return 
     */
    @Query(value = "select pid as id, ptitle as title, purl as url, content, tags from search.mv_searchindex " +
                   "where document @@ to_tsquery('german', :query) " +
                   "ORDER BY ts_rank(document, to_tsquery('german', :query)) DESC", nativeQuery = true)
    List<SearchResult> search(@Param("query") final String query);
    
    /**
     * Durchsucht den Suchindex nach einem Text in Kombination mit einem oder mehreren Tags
     * @param query
     * @param tags
     * @return 
     */
    @Query(value = "select pid, ptitle from search.mv_searchindex where document @@ to_tsquery(':query') " +
            "and tags @> array[:tags] " +
            "ORDER BY ts_rank(document, to_tsquery('german', ':query')) DESC"
            , nativeQuery = true)
    List<SearchResult> search(@Param("query") final String query, @Param("tags") final String tags);
    
    /**
     * Durchsucht den Suchindex nach einem oder mehreren Tags
     * @param tags
     * @return 
     */
    @Query(value = "select pid, ptitle from search.mv_searchindex where tags @> array[:tags]", nativeQuery = true)
    List<SearchResult> searchTags(@Param("tags") final String tags); 
    
    /**
     * Durchsucht Tags in der Autovervollständigung,
     * für die Eingabe der Tags beim Artikelbearbeiten
     * @param tag Wird mit :* für die Vervollständigung übergeben
     * @param pageable
     * @return 
     */
    @Query(value        = "select wlabel as value from search.mv_suggestions where word @@ to_tsquery('simple', :tag) and wtype = 2 limit 10", 
            countQuery  = "select count(*) from search.mv_suggestions where word @@ to_tsquery('simple', :tag) and wtype = 2",
            nativeQuery = true)
    List<TagSuggestion> suggestTag(@Param("tag") final String tag);
    
    /**
     * Durchsucht Artikeltitel in der Autovervollständigung
     * @param title
     * @return 
     */
    @Query(value = "select wtype, wlabel from search.mv_suggestions where word @@ to_tsquery('simple', '?1:*') and wtype = 1", nativeQuery = true)
    List<SuggestionResult> suggestTitle(@Param("title") final String title);
    
    /**
     * Durchsucht alles - für die Schnellsuche (Autovervollständigung)
     * @param qry
     * @return 
     */
    @Query(value = "select wtype, wlabel from search.mv_suggestions where word @@ to_tsquery('simple', ':qry:*')", nativeQuery = true)
    List<SuggestionResult> suggestAnything(@Param("qry") final String qry);
        
    
}
