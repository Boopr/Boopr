package dog.boopr.boopr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dog.boopr.boopr.models.Breed;

@Repository
public interface BreedRespository extends JpaRepository<Breed, Long> {
    
    List<Breed> findAll();
    Breed getOne(Long id);

}