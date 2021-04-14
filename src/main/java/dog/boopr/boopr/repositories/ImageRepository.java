package dog.boopr.boopr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dog.boopr.boopr.models.Boop;
import dog.boopr.boopr.models.Image;
import dog.boopr.boopr.models.User;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    
    List<Image> findAllByUser(User user);
    List<Image> findAllByDog(User user);
    Image getOne(long id);
 
}
