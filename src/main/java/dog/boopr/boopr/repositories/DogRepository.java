package dog.boopr.boopr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dog.boopr.boopr.models.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long>{
    Dog findByIdDog(Long id);
    List<Dog> findByUserId(Long id);

    @Query("SELECT d.id FROM DOG d, User u where u.username=?1 AND d.id = u.id")
    List<Dog> findByUsername(String username);
    
}