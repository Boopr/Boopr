package dog.boopr.boopr;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import dog.boopr.boopr.models.Breed;
import dog.boopr.boopr.models.Dog;

public class BooprModelTests {

    private Dog pup;
    private List<Breed> breedlist;

    @Before
    public void setUp(){
        this.breedlist = new ArrayList<>();
        this.breedlist.add(new Breed("Pitbull"));
        this.pup = new Dog("Pickles",
        breedlist,
        false,
        "I'm a crunchy boi");
    }
    
    @Test
    public void isPupObjectSame(){
        // //give
        // List<Breed> breedlist = new ArrayList<>();
        // breedlist.add(new Breed("Pitbull"));
        // Dog pup = new Dog("Pickles",
        // breedlist,
        // false,
        // "I'm a crunchy boi");
        Object clonedPup = this.pup;
        // //when
        // boolean exists = underTest.selectExistsName("Pickles");
        //then
        assertSame(pup,clonedPup);

    }
    
}
