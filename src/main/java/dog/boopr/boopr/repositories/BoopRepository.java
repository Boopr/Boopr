package dog.boopr.boopr.repositories;

import dog.boopr.boopr.models.Boop;
import dog.boopr.boopr.models.Image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoopRepository extends JpaRepository<Boop, Long> {
    List<Boop> findByImageId(Image image);
}
