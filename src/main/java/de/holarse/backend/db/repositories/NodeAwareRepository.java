package de.holarse.backend.db.repositories;

import de.holarse.backend.view.NodeStatisticsView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NodeAwareRepository {
    
    @Query("SELECT nextval('node_sequence')")
    int nextNodeId();

    @Query(value = "select to_char(stdate, 'YYYY-MM-DD') as time, " +
            "count(np.nodeid) as amount " +
            "from generate_series(cast((now() - interval '1 day' * :days) as date), " +
            "                     cast(now() as date), " +
            "                     interval '1 day') as stdate " +
            "left join node_pagevisits np on cast(np.accessed as date) = stdate " +
            "where (np.nodeid = :nodeId or np.nodeid is null) " +
            "group by to_char(stdate, 'YYYY-MM-DD') " +
            "order by to_char(stdate, 'YYYY-MM-DD')", nativeQuery = true)
    List<NodeStatisticsView> getDailyStats(@Param("nodeId") final Integer nodeId, @Param("days") final int days);

    @Query(value = "select to_char(stdate, 'YYYY-MM') as time, " +
                   "count(np.nodeid) as amount " +
                   "from generate_series(cast((now() - interval '1 month' * :months) as date), " +
                   "                     cast(now() as date), " +
                   "                     interval '1 day') as stdate " +
                   "left join node_pagevisits np on cast(np.accessed as date) = stdate " +
                   "where (np.nodeid = :nodeId or np.nodeid is null) " +
                   "group by to_char(stdate, 'YYYY-MM') " +
                   "order by to_char(stdate, 'YYYY-MM')", nativeQuery = true)
    List<NodeStatisticsView> getMonthlyStats(@Param("nodeId") final Integer nodeId, @Param("months") final int months);


}
