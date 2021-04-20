package dog.boopr.boopr.controllers;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import dog.boopr.boopr.repositories.BreedRespository;


@RestController
public class BreedController {

    @Autowired
    private BreedRespository breedDao;

    //Get breeds
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

    //Delete Breeds
    @RequestMapping(value="/api/breed/delete/{id}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public String deleteBreed(
        @PathVariable Long id){
            try{
                Breed breed = breedDao.getOne(id);
                breedDao.delete(breed);
                JSONObject response = new JSONObject();
                response.put("message","Deleted Breed!");
                return response.toString();
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }      
        }
        
    //Add Breeds
    @RequestMapping(value="/api/breed/add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public String AddBreed(
        HttpServletRequest request
    ) throws JSONException{
        //TODO: Add validation
        try{

            String breedName = request.getParameter("breed");
            
            Breed breed = new Breed(breedName);
            breedDao.save(breed);
            JSONObject response = new JSONObject();
            response.put("message","New breed added!");
            return response.toString();
        }catch(Exception e){
            e.printStackTrace();
            JSONObject err = new JSONObject();
            err.put("error", "Something went wrong" );
            return err.toString();
        }


    }
    
}
