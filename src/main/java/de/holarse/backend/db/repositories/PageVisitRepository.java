package de.holarse.backend.db.repositories;

import de.holarse.backend.db.PageVisit;
import de.holarse.web.admin.PageVisitMainResult;
import de.holarse.web.admin.StatisticSearchResult;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PageVisitRepository extends CrudRepository<PageVisit, Long> {

    @Query(value = "select url, count(url) as count from pagevisits group by url order by count(url) desc", nativeQuery = true)
    List<PageVisitMainResult> getMainResults();

    @Query(value = "select searchword, count(searchword) as count from pagevisits group by searchword order by count(searchword) desc", nativeQuery = true)
    List<StatisticSearchResult> getSearches();    
    
    ;
    
}
