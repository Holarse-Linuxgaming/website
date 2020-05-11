package de.holarse.backend.db.repositories;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserStat;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.function.Supplier;

public interface UserStatRepository extends CrudRepository<UserStat, Long> {
    Optional<UserStat> findByUser(User user);

    default Supplier<UserStat> createDefaultUserStat(final User user) {
        return () -> {
            UserStat us = new UserStat();
            us.setUser(user);
            us.setCreated(OffsetDateTime.now());
            return us;
        };
    }

    @Modifying
    @Query("update UserStat us set us.lastAction = :last where us.user = :u")
    void updateLastAction(@Param("u") final User user, @Param("last") final OffsetDateTime lastAction);
}
