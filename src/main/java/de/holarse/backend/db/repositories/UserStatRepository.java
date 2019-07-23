package de.holarse.backend.db.repositories;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserStatRepository extends CrudRepository<UserStat, Long> {
    Optional<UserStat> findByUser(User user);
}
