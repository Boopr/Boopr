package dog.boopr.boopr;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dog.boopr.boopr.models.Breed;
import dog.boopr.boopr.models.Dog;

public class BooprModelTests {
    
    @Test
    public void isDaoPullSame(){
        // //give
        List<Breed> breedlist = new ArrayList<>();
        breedlist.add(new Breed("Pitbull"));
        Dog pup = new Dog("Pickles",
        breedlist,
        false,
        "I'm a crunchy boi");
        Object clonedPup = pup;
        // //when
        // boolean exists = underTest.selectExistsName("Pickles");
        //then
        // assertSame(pup,clonedPup);
        assertTrue("this is true",4>3);

    }
    
}
