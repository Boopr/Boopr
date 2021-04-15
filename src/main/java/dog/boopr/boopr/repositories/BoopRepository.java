package dog.boopr.boopr.repositories;

import dog.boopr.boopr.models.Boop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoopRepository extends JpaRepository<Boop, Long> {
    
}
