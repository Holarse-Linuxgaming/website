package de.holarse.backend.db.repositories;

import de.holarse.backend.db.NodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeStatusRepository extends JpaRepository<NodeStatus, Integer> {
    
}
