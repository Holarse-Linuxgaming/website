package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Node;
import de.holarse.search.SearchResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SearchRepository extends JpaRepository<Node, Long> {

    @Modifying
    @Query(value = "REFRESH MATERIALIZED VIEW search.mv_searchindex", nativeQuery = true)
    void update();

    @Query(value = "select pid as id, ptitle as title, purl as url, content, tags from search.mv_searchindex " +
                   "where document @@ to_tsquery('german', :query) " +
                   "ORDER BY ts_rank(document, to_tsquery('german', :query)) DESC", nativeQuery = true)
    List<SearchResult> search(@Param("query") final String query);
    
    @Query(value = "select pid, ptitle from search.mv_searchindex where document @@ to_tsquery(':query') " +
            "and tags @> array[:tags] " +
            "ORDER BY ts_rank(document, to_tsquery('german', ':query')) DESC"
            , nativeQuery = true)
    List<SearchResult> search(@Param("query") final String query, @Param("tags") final String tags);
    
    @Query(value = "select pid, ptitle from search.mv_searchindex where tags @> array[:tags]", nativeQuery = true)
    List<SearchResult> searchTags(@Param("tags") final String tags);    
    
}
