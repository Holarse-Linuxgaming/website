package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
