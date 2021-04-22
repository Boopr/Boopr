package dog.boopr.boopr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dog.boopr.boopr.models.PackLeader;
import dog.boopr.boopr.models.User;

@Repository
public interface PackLeaderRepository extends JpaRepository<PackLeader, Long>{

    List<PackLeader> findByIdEquals(Long id);
    List<PackLeader> findAllByUser(User owner);
    
}
