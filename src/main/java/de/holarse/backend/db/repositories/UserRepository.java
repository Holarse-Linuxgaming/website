package de.holarse.backend.db.repositories;

import de.holarse.backend.db.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    
    public User findByLogin(final String login);
    public User findByVerificationKey(final String verificationKey);
    
//    @Query("select u from User u where u.login = :login or u.email = :email")
//    public User findByLoginOrEmail(@Param("login") final String login, @Param("email") final String email);
//    
}
