package dog.boopr.boopr.controllers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dog.boopr.boopr.models.Breed;
import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.BreedRespository;
import dog.boopr.boopr.repositories.DogRepository;
import dog.boopr.boopr.repositories.UserRepository;

@RestController
public class RestDogController {
    
    @Autowired
    private BreedRespository breedDao;

    @Autowired
    private DogRepository dogDao;

    @Autowired
    private UserRepository userDao;


    @RequestMapping(value="/api/breeds", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getBreeds() throws JSONException{


        JSONArray breeds = new JSONArray();

        List<Breed> breedData = breedDao.findAll();

        for( Breed b : breedData){

            JSONObject breed = new JSONObject();
            breed.put("id", b.getId());
            breed.put("name", b.getBreed());


            breeds.put(breed);

        }
        
        return breeds.toString();
    }

    @RequestMapping(value="/api/dogs", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getDogs() throws JSONException{


        JSONArray dogs = new JSONArray();

        List<Dog> dogData = dogDao.findAll();

        for( Dog d : dogData){

            JSONArray breeds = new JSONArray();
            for(Breed b: d.getBreeds()){
                breeds.put(b.getBreed());
            }
            JSONObject owner = new JSONObject();
            owner.put("id",d.getOwner().getId());
            owner.put("username",d.getOwner().getUsername());
            owner.put("id",d.getOwner().getEmail());
            JSONObject dog = new JSONObject();
            dog.put("id", d.getId());
            dog.put("name", d.getName());
            dog.put("bio",d.getBio());
            dog.put("breed",breeds);
            dog.put("owner",owner);
            dog.put("sex",d.getSex());
            dog.put("lat",d.getLat());
            dog.put("lon",d.getLon());


            dogs.put(dog);

        }
        
        return dogs.toString();
    }

    @RequestMapping(value="/api/owner/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getDogsByOwner(@PathVariable Long id) throws JSONException{


        JSONArray dogs = new JSONArray();

        User user = userDao.findByIdEquals(id).get(0);

        List<Dog> userDogs = user.getDogs();

        for( Dog d : userDogs){

            JSONArray breeds = new JSONArray();
            for(Breed b: d.getBreeds()){
                breeds.put(b.getBreed());
            }
            JSONObject owner = new JSONObject();
            owner.put("id",d.getOwner().getId());
            owner.put("username",d.getOwner().getUsername());
            owner.put("id",d.getOwner().getEmail());
            JSONObject dog = new JSONObject();
            dog.put("id", d.getId());
            dog.put("name", d.getName());
            dog.put("bio",d.getBio());
            dog.put("breed",breeds);
            dog.put("owner",owner);
            dog.put("sex",d.getSex());
            dog.put("lat",d.getLat());
            dog.put("lon",d.getLon());


            dogs.put(dog);

        }
        
        return dogs.toString();
    }

    @RequestMapping(value="/api/dogs/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getDogsByID(@PathVariable Long id) throws JSONException{


        JSONArray dogs = new JSONArray();

        Dog dog = dogDao.getOne(id);

            JSONArray breeds = new JSONArray();
            for(Breed b: dog.getBreeds()){
                breeds.put(b.getBreed());
            }
            JSONObject owner = new JSONObject();
            owner.put("id",dog.getOwner().getId());
            owner.put("username",dog.getOwner().getUsername());
            owner.put("id",dog.getOwner().getEmail());
            JSONObject jsondog = new JSONObject();
            jsondog.put("id", dog.getId());
            jsondog.put("name", dog.getName());
            jsondog.put("bio",dog.getBio());
            jsondog.put("breed",breeds);
            jsondog.put("owner",owner);
            jsondog.put("sex",dog.getSex());
            jsondog.put("lat",dog.getLat());
            jsondog.put("lon",dog.getLon());


            dogs.put(dog);

        
        return dogs.toString();
    }

}
