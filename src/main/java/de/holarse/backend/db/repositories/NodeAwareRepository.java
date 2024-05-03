package de.holarse.backend.db.repositories;

import de.holarse.backend.view.NodeStatisticsView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NodeAwareRepository {
    
    @Query("SELECT nextval('node_sequence')")
    int nextNodeId();    

    @Query(value = "SELECT to_char(np.accessed, 'YYYY-MM-DD') as time, sum(1) as amount from node_pagevisits np " +
            "WHERE np.nodeid = :nodeId and np.accessed >= now() - interval '1 day' * :days " +
            "GROUP BY to_char(np.accessed, 'YYYY-MM-DD') " +
            "ORDER BY to_char(np.accessed, 'YYYY-MM-DD')", nativeQuery = true)
    List<NodeStatisticsView> getDailyStats(@Param("nodeId") final Integer nodeId, @Param("days") final int days);

    @Query(value = "SELECT to_char(np.accessed, 'YYYY-MM') as time, sum(1) as amount from node_pagevisits np " +
            "WHERE np.nodeid = :nodeId and np.accessed >= now() - interval '1 month' * :months " +
            "GROUP BY to_char(np.accessed, 'YYYY-MM') " +
            "ORDER BY to_char(np.accessed, 'YYYY-MM')", nativeQuery = true)
    List<NodeStatisticsView> getMonthlyStats(@Param("nodeId") final Integer nodeId, @Param("months") final int months);


}
