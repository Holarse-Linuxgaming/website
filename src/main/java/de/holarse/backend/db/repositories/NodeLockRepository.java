package de.holarse.backend.db.repositories;

import de.holarse.backend.db.NodeLock;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface NodeLockRepository extends CrudRepository<Long, NodeLock>, QuerydslPredicateExecutor<NodeLock> {
}
