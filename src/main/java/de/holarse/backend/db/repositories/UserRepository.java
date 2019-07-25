package de.holarse.backend.db.repositories;

import de.holarse.backend.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long>, OldIdRepository<User> {
    
    @Query("SELECT u FROM User u WHERE u.login = :login")
    User findByLogin(@Param("login") final String login);
    User findByVerificationKey(final String verificationKey);
}
