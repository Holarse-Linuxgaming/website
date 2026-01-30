package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    
    @Query("select r from Role r where r.code = :code")
    Role findByCode(@Param("code") final String code);
    
}
