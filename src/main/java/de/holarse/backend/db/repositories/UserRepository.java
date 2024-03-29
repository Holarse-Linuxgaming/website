package de.holarse.backend.db.repositories;

import de.holarse.backend.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByLogin(final String login);
    User findByEmail(final String email);
    
    User findByLoginOrEmail(final String login, final String email);
        
//    @Query(value = "select u.* from users u inner join user_status us on us.id = u.id "
//            + "where us.verification_hash = :verificationHash "
//            + "and not locked and not verified and verification_hash_validuntil > current_timestamp", nativeQuery = true)
//    Optional<User> findByVerificationHash(@Param("verificationHash") final String verificationHash);
    
//    @Override
//    @Query(value = "select u.* from users u inner join user_status us on u.user_status_id = us.id ORDER BY ?#{#pageable}", 
//           countQuery= "select count(1) from users u inner join user_status us on u.user_status_id = us.id ORDER BY ?#{#pageable}", nativeQuery = true)
//    Page<User> findAll(final Pageable pageable);
    
}
