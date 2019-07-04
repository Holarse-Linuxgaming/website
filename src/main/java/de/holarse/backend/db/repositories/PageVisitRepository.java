package de.holarse.backend.db.repositories;

import de.holarse.backend.db.PageVisit;
import de.holarse.backend.views.PageVisitResult;
import de.holarse.web.admin.PageVisitMainResult;
import de.holarse.web.admin.StatisticSearchResult;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PageVisitRepository extends CrudRepository<PageVisit, Long> {

    @Query(value = "select url, count(url) as count from pagevisits group by url order by count(url) desc", nativeQuery = true)
    List<PageVisitMainResult> getMainResults();

    @Query(value = "select searchword, count(searchword) as count from pagevisits group by searchword order by count(searchword) desc", nativeQuery = true)
    List<StatisticSearchResult> getSearches();    
    
    @Query(value = "select cast(cast(accessed as date) as varchar) as date, count(id) as visits from pagevisits where nodeid = :nodeId and accessed between :fromDate and :untilDate group by cast(accessed as date) order by date", nativeQuery = true)    
    List<PageVisitResult> getNodeVists(@Param("nodeId") final Long nodeId, @Param("fromDate") final LocalDateTime fromDate, @Param("untilDate") LocalDateTime untilDate);
    
}
