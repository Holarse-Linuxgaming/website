package de.holarse.backend.db.repositories;

import de.holarse.backend.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends
        JpaRepository<User, Long>,
        SluggableRepository<User>,
        OldIdRepository<User> {

    User findByLogin(final String login);
    boolean existsByLoginOrEmail(final String login, final String email);
    User findByVerificationKey(final String verificationKey);
}
