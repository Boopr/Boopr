package dog.boopr.boopr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dog.boopr.boopr.models.Boop;
import dog.boopr.boopr.models.Image;
import dog.boopr.boopr.models.User;

@Repository
public interface BoopRepository extends JpaRepository<Boop, Long> {
    
    List<Boop> findAllByUser(User user);
    List<Boop> findAllByImage(Image image);
    

}
