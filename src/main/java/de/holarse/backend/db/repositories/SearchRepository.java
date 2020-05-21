package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Node;
import de.holarse.search.SearchResult;
import de.holarse.search.SuggestionResult;
import de.holarse.search.TagSuggestion;
import java.util.List;
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
     * @param offset
     * @param pageSize
     * @return 
     */
    @Query(value = "select pid as id, ptitle as title, psubtitles as subtitles, purl as url, content, tags from search.mv_searchindex " +
                   "where document @@ websearch_to_tsquery('german', :query) " +
                   "ORDER BY ts_rank(document, websearch_to_tsquery('german', :query)) DESC " +
                   "OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY",
           nativeQuery = true)
    List<SearchResult> search(@Param("query") final String query, @Param("offset") final long offset, @Param("pageSize") final int pageSize);
    
    @Query(value = "select count(*) from search.mv_searchindex where document @@ websearch_to_tsquery('german', :query)", nativeQuery = true)
    long searchCount(@Param("query") final String query);
    
    /**
     * Durchsucht den Suchindex nach einem Text in Kombination mit einem oder mehreren Tags
     * @param query
     * @param tags
     * @param offset
     * @param pageSize
     * @return 
     */
    @Query(value = "select pid as id, ptitle as title, psubtitles as subtitles, purl as url, content, tags from search.mv_searchindex where document @@ websearch_to_tsquery('german', :query) " +
            "and string_to_array(tags, ';') @> string_to_array(:tags, ';') " +
            "ORDER BY ts_rank(document, websearch_to_tsquery('german', :query)) DESC " +
            "OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY"
            , nativeQuery = true)
    List<SearchResult> search(@Param("query") final String query, @Param("tags") final String tags, @Param("offset") final long offset, @Param("pageSize") final int pageSize);
    
    /**
     * Ermittelt die Ergebnismenge einer Suche mit Query und Tags
     * @param tags
     * @param query
     * @return 
     */
    @Query(value = "select count(*) from search.mv_searchindex where document @@ websearch_to_tsquery('german', :query) " +
            "and string_to_array(tags, ';') @> string_to_array(:tags, ';')", nativeQuery = true)
    long searchCount(@Param("tags") final String tags, @Param("query") final String query);    
    
    /**
     * Ermittelt die Ergebnismenge einer Suche mit nur Tags
     * @param tags
     * @return 
     */
    @Query(value = "select count(*) from search.mv_searchindex where string_to_array(tags, ';') @> string_to_array(:tags, ';')", nativeQuery = true)
    long searchCountTags(@Param("tags") final String tags);     
    
    /**
     * Durchsucht den Suchindex nach einem oder mehreren Tags
     * @param tags Der formatierte Tag-String, als 'meinTag;nocheiner'
     * @param offset
     * @param pageSize
     * @return 
     */
    @Query(value = "select pid as id, ptitle as title, psubtitles as subtitles, purl as url, content, tags from "
                 + "search.mv_searchindex where string_to_array(tags, ';') @> string_to_array(:tags, ';')"
                 + "OFFSET :offset ROWS FETCH NEXT :pageSize ROWS ONLY",
           nativeQuery = true)
    List<SearchResult> searchTags(@Param("tags") final String tags, @Param("offset") final long offset, @Param("pageSize") final int pageSize); 
    
    /**
     * Durchsucht Tags in der Autovervollständigung,
     * für die Eingabe der Tags beim Artikelbearbeiten
     * @param tag Wird mit :* für die Vervollständigung übergeben
     * @return 
     */
    @Query(value = "select wlabel as value from search.mv_suggestions where word @@ to_tsquery('simple', :tag) and wtype = 'tag' ORDER BY use_count desc", nativeQuery = true)
    List<TagSuggestion> suggestTag(@Param("tag") final String tag);
    
    /**
     * Durchsucht Artikeltitel in der Autovervollständigung
     * @param title
     * @return 
     */
    @Query(value = "select wtype, wlabel from search.mv_suggestions where word @@ to_tsquery('english', :title) and wtype = 'title' ORDER BY ts_rank(word, to_tsquery('english', :title)) asc", nativeQuery = true)
    List<SuggestionResult> suggestTitle(@Param("title") final String title);
    
    /**
     * Durchsucht alles - für die Schnellsuche (Autovervollständigung)
     * @param qry
     * @return 
     */
    @Query(value = "select wtype, wlabel from " +
    "( " +
    "(select wtype, wlabel, ts_rank(word, to_tsquery('english', :qry)) * 1000 as rank from search.mv_suggestions where word @@ to_tsquery('english', :qry) and wtype='title' ORDER BY ts_rank(word, to_tsquery('english', :qry)) asc) " +
    "union " +
    "(select wtype, wlabel, use_count as rank from search.mv_suggestions where word @@ to_tsquery('simple', :qry) and wtype = 'tag' order by use_count desc) " +
    ") "+
    "as suggestion " +
    "order by rank", nativeQuery = true)
    List<SuggestionResult> suggestAnything(@Param("qry") final String qry);
        
    
}
