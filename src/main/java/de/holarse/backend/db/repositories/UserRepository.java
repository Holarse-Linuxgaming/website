package de.holarse.backend.db.repositories;

import de.holarse.backend.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long>, OldIdRepository<User> {
    
    User findByLogin(final String login);
    User findByVerificationKey(final String verificationKey);
}
