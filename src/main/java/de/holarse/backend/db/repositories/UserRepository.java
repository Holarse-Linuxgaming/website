package de.holarse.backend.db.repositories;

import de.holarse.backend.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    
    public User findByLogin(final String login);
    public User findByVerificationKey(final String verificationKey);
    
//    @Query("select u from User u where u.login = :login or u.email = :email")
//    public User findByLoginOrEmail(@Param("login") final String login, @Param("email") final String email);
//    
}
