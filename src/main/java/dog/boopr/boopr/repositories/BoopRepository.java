package dog.boopr.boopr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dog.boopr.boopr.models.Boop;

@Repository
public interface BoopRepository extends JpaRepository<Boop, Long> {
    
}
