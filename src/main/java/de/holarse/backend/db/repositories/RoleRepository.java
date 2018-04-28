package de.holarse.backend.db.repositories;

import de.holarse.backend.db.Role;
import java.util.Optional;
import javax.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Id> {
    
    public Optional<Role> findByCodeIgnoreCase(final String code);
    
}
