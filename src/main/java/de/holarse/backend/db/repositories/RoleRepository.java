package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    
    @Query(value = "select r.* from roles r where r.code = :code", nativeQuery = true)
    Role findByCode(@Param("code") final String code);
    
}
