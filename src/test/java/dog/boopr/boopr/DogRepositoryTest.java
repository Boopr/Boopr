package dog.boopr.boopr;
import org.junit.Assert;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import dog.boopr.boopr.models.Breed;
import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.repositories.DogRepository;

@DataJpaTest
public class DogRepositoryTest {
    
    @Autowired
    private DogRepository dogTest;

    @Test
    public void isDaoPullSame(){
        // //give
        List<Breed> breedlist = new ArrayList<>();
        breedlist.add(new Breed("Pitbull"));
        Dog pup = new Dog("Pickles",
        breedlist,
        false,
        "I'm a crunchy boi");
        // Object clonnedPup = pup;
        dogTest.save(pup);
        // //when
        // boolean exists = underTest.selectExistsName("Pickles");
        //then
        assertSame(pup,dogTest.findDogByName("Pickles").get(0));

    }
}
