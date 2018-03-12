package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Node;
import de.holarse.web.search.SearchResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SearchRepository extends JpaRepository<Node, Long> {

    @Modifying
    @Query(value = "REFRESH MATERIALIZED VIEW search_index", nativeQuery = true)
    void update();

    @Query(value = "select pid as id, ptitle as title, pcontent as content from search_index where search_index.document @@ to_tsquery('holarse_de', :query) order by ts_rank(search_index.document, to_tsquery('holarse_de', :query)) desc", nativeQuery = true)
    List<SearchResult> search(@Param("query") final String query);
    
}
