package de.holarse.backend.db.repositories;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserStat;
import org.springframework.data.repository.CrudRepository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.function.Supplier;

public interface UserStatRepository extends CrudRepository<UserStat, Long> {
    Optional<UserStat> findByUser(User user);

    default Supplier<UserStat> createDefaultUserStat() {
        return () -> {
            UserStat us = new UserStat();
            us.setCreated(OffsetDateTime.now());
            return us;
        };
    }
}
