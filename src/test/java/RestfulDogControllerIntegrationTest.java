import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dog.boopr.boopr.repositories.DogRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(
  SpringBootTest.WebEnvironment.MOCK,
  classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-integrationtest.properties")
public class RestfulDogControllerIntegrationTest{

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DogRepository dogDao;

    // write test cases here
}