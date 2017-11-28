package de.holarse.backend.repository;

import de.holarse.view.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author comrad
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByLogin(String login);
    
}