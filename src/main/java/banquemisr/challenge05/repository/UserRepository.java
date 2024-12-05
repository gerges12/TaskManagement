package banquemisr.challenge05.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import banquemisr.challenge05.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
  
  Optional<User> findByEmail(String email);
  
  @Query("SELECT u.id FROM User u WHERE u.username = :username")
  Optional<Long> findIdByUsername(String username);

}
