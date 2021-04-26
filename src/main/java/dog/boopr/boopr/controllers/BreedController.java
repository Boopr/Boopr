package dog.boopr.boopr.controllers;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dog.boopr.boopr.models.Breed;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.BreedRespository;
import dog.boopr.boopr.services.UserServices;


@RestController
public class BreedController {

    @Autowired
    private BreedRespository breedRespository;

    @Autowired
	private UserServices userDetailsService;

    @GetMapping("/api/breed")
    public String getBreeds() throws JSONException{

        List<Breed> breeds = breedRespository.findAll();
        JSONArray response = new JSONArray();

        for(Breed b : breeds){

            JSONObject breed = new JSONObject();
            breed.put("name", b.getBreed());
            breed.put("id", b.getId());
            response.put(breed);

        }
        
        return response.toString();

    }
    
    @PostMapping("/api/breed")
    public String addBreed(
        @RequestParam(required = true, name = "name") String name
    ) throws JSONException{

        User user = userDetailsService.getCurrentUser();

        if(userDetailsService.userIsRole(user, "admin")){

            Breed breed = new Breed(name); 
            breedRespository.save(breed);
            
            JSONObject response = new JSONObject();
            response.put("message","Breed added!");
            return response.toString();

        }

        JSONObject response = new JSONObject();
        response.put("error","You do not have permissions!");
        return response.toString();

    }

    @PutMapping("/api/breed/{id}")
    public String editBreed(
        @RequestParam(required = true, name = "name") String name,
        @PathVariable Long id
    ) throws JSONException{

        User user = userDetailsService.getCurrentUser();

        if(userDetailsService.userIsRole(user, "admin")){

            Breed breed = breedRespository.getOne(id);
            breed.setBreed(name);
            breedRespository.save(breed);
            
            JSONObject response = new JSONObject();
            response.put("message","Breed edited!");
            return response.toString();

        }

        JSONObject response = new JSONObject();
        response.put("error","You do not have permissions!");
        return response.toString();

    }

    @DeleteMapping("/api/breed/{id}")
    public String deleteBreed(
        @PathVariable Long id
    ) throws JSONException{

        User user = userDetailsService.getCurrentUser();

        if(userDetailsService.userIsRole(user, "admin")){

            Breed breed = breedRespository.getOne(id);
            breedRespository.delete(breed);
            
            JSONObject response = new JSONObject();
            response.put("message","Breed deleted!");
            return response.toString();

        }
        
        JSONObject response = new JSONObject();
        response.put("error","You do not have permissions!");
        return response.toString();

    }
    
}
