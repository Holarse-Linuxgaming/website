package de.holarse.backend.db.repositories;

import de.holarse.backend.db.UserStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
    
    @Query("from UserStatus us where us.verificationHash = :verificationHash and not us.locked and not us.verified and us.verificationHashValidUntil > current_timestamp")
    Optional<UserStatus> findByValidVerification(@Param("verificationHash") final String verificationHash);
    
}
