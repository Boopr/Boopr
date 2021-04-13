package dog.boopr.boopr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dog.boopr.boopr.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    List<User> findByIdEquals(Long id);
    List<User> findAll();
}