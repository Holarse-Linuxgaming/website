package de.holarse.website.repositories;

import de.holarse.website.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User findByEmail(@Param("email") String email);

    @Override
    List<User> findAll();
}
