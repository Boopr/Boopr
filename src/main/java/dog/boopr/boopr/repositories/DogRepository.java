package dog.boopr.boopr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.models.User;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long>{

    // @Query("SELECT d.id FROM DOG d, User u where u.username=?1 AND d.id = u.id")
    // List<Dog> findByUsername(String username);
    List<Dog> findAll();
    //Get one is a method that exists already for getting a dog by its ID
    Dog getOne(Long id);
    
    //We do a query to find all by the user object because it references everything we need anyways.
    List<Dog> findAllByOwner(User owner);

    List<Dog> findDogsByOwnerId(long parseLong);
}