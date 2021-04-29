package dog.boopr.boopr;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import dog.boopr.boopr.models.Breed;
import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.DogRepository;
import dog.boopr.boopr.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BooprApplication.class)
@AutoConfigureMockMvc
class BooprApplicationTests {

	private User testUser;
	private HttpSession httpSession;

	@Autowired
    private MockMvc mvc;

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
    private DogRepository dogDao;

    @Autowired
    private UserRepository userDao;

	private Dog expectedDog;
	private List<Breed> expectedBreeds;
	private Breed expectedBreed;

	// @Before
	// public void setUp(){
	// 	this.expectedBreeds = new ArrayList<>();
	// 	this.expectedBreed = new Breed("Pitbull");
	// 	expectedBreeds.add(expectedBreed);
	// 	this.expectedDog = new Dog("test",expectedBreeds,true,"I'm a testy boi");
	// }

	@Before
	public void setup() throws Exception{
		testUser = userDao.findByUsername("test4");


	}

	

}
