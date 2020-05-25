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

    @Query(value = "select url, count(url) as count from logging.accesslog group by url order by count(url) desc", nativeQuery = true)
    List<PageVisitMainResult> getMainResults();

    @Query(value = "select searchword, count(searchword) as count from logging.accesslog group by searchword order by count(searchword) desc", nativeQuery = true)
    List<StatisticSearchResult> getSearches();    
    
    //@Query(value = "select cast(cast(accessed as date) as varchar) as date, count(id) as visits from logging.accesslog where nodeid = :nodeId and accessed between :fromDate and :untilDate group by cast(accessed as date) order by date", nativeQuery = true)    
    @Query(value = "with calendar_range as ( " +
        "select " +
        "to_char(generate_series(cast(:fromDate as date), cast(:untilDate as date), '1 day'), 'YYYY-MM-DD') as date " +
    "), access_range as ( " +
        "select to_char(accessed, 'YYYY-MM-DD') as date, count(id) as visits from logging.accesslog " +
        "where nodeid = :nodeId and accessed between :fromDate and :untilDate " +
        "group by to_char(accessed, 'YYYY-MM-DD') order by date " +
    ") " +
    "select cr.date, coalesce(ar.visits, 0) as visits from calendar_range cr " +
    "left join access_range ar on cr.date = ar.date", nativeQuery = true)
    List<PageVisitResult> getNodeVists(@Param("nodeId") final Long nodeId, @Param("fromDate") final LocalDateTime fromDate, @Param("untilDate") LocalDateTime untilDate);
    
}
