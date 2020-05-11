package de.holarse.backend.db.repositories;

import de.holarse.backend.db.User;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends
        JpaRepository<User, Long>,
        SluggableRepository<User>,
        OldIdRepository<User> {

    User findByLogin(final String login);
    boolean existsByLoginOrEmail(final String login, final String email);
    // TODO Optional    
    User findByVerificationKey(final String verificationKey);

    List<User> findByVerified(final Pageable pageable);
}
