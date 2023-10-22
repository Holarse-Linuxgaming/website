package de.holarse.backend.db.repositories;

import de.holarse.backend.db.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiUserRepository extends JpaRepository<ApiUser, Integer> {
    
    ApiUser findByLogin(final String login);

}
