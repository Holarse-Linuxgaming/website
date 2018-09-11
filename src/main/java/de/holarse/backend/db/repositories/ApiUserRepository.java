package de.holarse.backend.db.repositories;

import de.holarse.backend.db.ApiUser;
import org.springframework.data.repository.CrudRepository;

public interface ApiUserRepository extends CrudRepository<ApiUser, Long> {
    
    public ApiUser findByLogin(final String login);

}
