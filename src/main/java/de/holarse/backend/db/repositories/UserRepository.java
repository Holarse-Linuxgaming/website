package de.holarse.backend.db.repositories;

import de.holarse.backend.db.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, OldIdRepository<User> {
    
    public User findByLogin(final String login);
    public User findByVerificationKey(final String verificationKey);
   
}
