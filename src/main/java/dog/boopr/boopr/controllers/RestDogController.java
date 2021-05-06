package dog.boopr.boopr.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dog.boopr.boopr.models.Boop;
import dog.boopr.boopr.models.Breed;
import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.models.Image;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.BoopRepository;
import dog.boopr.boopr.repositories.DogRepository;
import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.services.UserServices;
import dog.boopr.boopr.utils.FileUtil;

@RestController
public class RestDogController {

    @Autowired
    private ValidatorFactory vFactory;

    @Autowired
    private DogRepository dogDao;

    @Autowired
    private BoopRepository boopDao;

    @Autowired
    private UserRepository userDao;

    @Value("${mapbox-key}")
    private String mapboxApiKey;

    @Autowired
    private UserServices userService;

    /**
     * This returns the mapbox api key from application.properties
     * @return
     */
    @RequestMapping(path = "/js/keys.js", produces ="application/javascript")
    @ResponseBody
    public String apiKey(){
        return "const apiKey = '"+mapboxApiKey+"' ; export default apiKey";
    }

    /**
     * This route gives back a list of all dogs in the database
     * @throws JSONException
     */
    @RequestMapping(value="/api/dogs", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getDogs() throws JSONException{


        JSONArray dogs = new JSONArray();

        List<Dog> dogData = dogDao.findAll();

        for( Dog d : dogData){

            JSONArray breeds = new JSONArray();
            for(Breed b: d.getBreeds()){
                JSONObject breed = new JSONObject();
                breed.put("name",b.getBreed());
                breed.put("id",b.getId());
                breeds.put(breed);
            }
            Long allBoops = 0L;
            JSONArray images = new JSONArray();
            for(Image i: d.getImages()){
                JSONObject img = new JSONObject();
                allBoops += i.getBoops().size();
                img.put("id",i.getId());
                img.put("url",i.getUrl());
                img.put("boops",i.getBoops().size());
                images.put(img);
            }
            JSONObject owner = new JSONObject();
            owner.put("id",d.getOwner().getId());
            owner.put("username",d.getOwner().getUsername());
            owner.put("email",d.getOwner().getEmail());
            JSONObject dog = new JSONObject();
            dog.put("id", d.getId());
            dog.put("name", d.getName());
            dog.put("images",images);
            dog.put("bio",d.getBio());
            dog.put("breed",breeds);
            dog.put("owner",owner);
            dog.put("allBoops", allBoops);
            dog.put("sex",d.getSex());
            dog.put("lat",d.getLat());
            dog.put("lon",d.getLon());


            dogs.put(dog);

        }
        
        return dogs.toString();
    }

    /**
     * This route gives back a list of all dogs in the database under a owner/user
     * @param id The id of the owner/user you are reciving all dogs about
     * @throws JSONException
     */
    @RequestMapping(value="/api/dogs/owner/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getDogsByOwner(@PathVariable Long id) throws JSONException{


        JSONArray dogs = new JSONArray();

        User user = userDao.findByIdEquals(id).get(0);

        List<Dog> userDogs = user.getDogs();

        for( Dog d : userDogs){

            JSONArray breeds = new JSONArray();
            for(Breed b: d.getBreeds()){
                JSONObject breed = new JSONObject();
                breed.put("name",b.getBreed());
                breed.put("id",b.getId());
                breeds.put(breed);
            }
            Long allBoops = 0L;
            Long total = 0L;
            JSONArray images = new JSONArray();
            for(Image i: d.getImages()){
                JSONObject img = new JSONObject();
                allBoops += i.getBoops().size();
                img.put("id",i.getId());
                img.put("url",i.getUrl());
                img.put("boops",i.getBoops().size());
                images.put(img);
            }
            JSONObject owner = new JSONObject();
            owner.put("id",d.getOwner().getId());
            owner.put("username",d.getOwner().getUsername());
            owner.put("id",d.getOwner().getEmail());
            JSONObject dog = new JSONObject();
            dog.put("id", d.getId());
            dog.put("name", d.getName());
            dog.put("bio",d.getBio());
            dog.put("images",images);
            dog.put("breed",breeds);
            dog.put("owner",owner);
            dog.put("allBoops", allBoops);
            dog.put("sex",d.getSex());
            dog.put("lat",d.getLat());
            dog.put("lon",d.getLon());


            dogs.put(dog);

        }
        
        return dogs.toString();
    }

    /**
     * This route gives back a list of all dogs in the database under a owner/user
     * @param id The id of the owner/user you are reciving all dogs about
     * @throws JSONException
     */
    @RequestMapping(value="/api/dogs/owner/mydogs", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getSelfDogs() throws JSONException{


        JSONArray dogs = new JSONArray();

        User user = userService.getCurrentUser();
        if(user == null){
            JSONObject response = new JSONObject();
            response.put("error","You are not logged in!");
            return response.toString();
        }

        List<Dog> userDogs = user.getDogs();

        //checks if user is new
        if(userDogs == null || userDogs.size() == 0){
            List<Boop> boops = boopDao.findAllByUser(user);
            if(boops.size() != 0){
                JSONObject firstTime = new JSONObject();
                firstTime.put("notNew","true");
                dogs.put(firstTime);
                return dogs.toString();
            }
            
        }

        for( Dog d : userDogs){

            JSONArray breeds = new JSONArray();
            for(Breed b: d.getBreeds()){
                JSONObject breed = new JSONObject();
                breed.put("name",b.getBreed());
                breed.put("id",b.getId());
                breeds.put(breed);
            }
            Long allBoops = 0L;
            Long total = 0L;
            JSONArray images = new JSONArray();
            for(Image i: d.getImages()){
                JSONObject img = new JSONObject();
                allBoops+=total;
                img.put("id",i.getId());
                img.put("url",i.getUrl());
                img.put("boops", i.getBoops().size());
                allBoops += i.getBoops().size();
                images.put(img);
            }
            JSONObject owner = new JSONObject();
            owner.put("id",d.getOwner().getId());
            owner.put("username",d.getOwner().getUsername());
            owner.put("id",d.getOwner().getEmail());
            JSONObject dog = new JSONObject();
            dog.put("id", d.getId());
            dog.put("name", d.getName());
            dog.put("bio",d.getBio());
            dog.put("images",images);
            dog.put("breed",breeds);
            dog.put("owner",owner);
            dog.put("allBoops", allBoops);
            dog.put("sex",d.getSex());
            dog.put("lat",d.getLat());
            dog.put("lon",d.getLon());


            dogs.put(dog);

        }
        
        return dogs.toString();
    }

    /**
     * This route gives back all information about a single dog by ID
     * @param id The id of the dog you are retriveing 
     * @throws JSONException
     */
    @RequestMapping(value="/api/dogs/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getDogsByID(@PathVariable Long id) throws JSONException{

        long totalDogs = dogDao.findAll().size()-1;
        User user = userService.getCurrentUser();
        Dog dog = dogDao.getOne(id);

            JSONArray breeds = new JSONArray();
            for(Breed b: dog.getBreeds()){
                JSONObject breed = new JSONObject();
                breed.put("name",b.getBreed());
                breed.put("id",b.getId());
              
                breeds.put(breed);
            }
            JSONObject owner = new JSONObject();
            owner.put("id",dog.getOwner().getId());
            owner.put("username",dog.getOwner().getUsername());
            owner.put("id",dog.getOwner().getEmail());
            JSONArray jsonImages = new JSONArray();
            List<Image> images = dog.getImages();
            // JSONObject boop = new JSONObject();
            // JSONArray boops = new JSONArray();
            Long allBoops = 0L;
            Long total = 0L;
            for( Image i : images){
                
                allBoops += i.getBoops().size();
                JSONObject img = new JSONObject();
                img.put("boops", i.getBoops().size());

                //checks if you booped an image
                if(i.getBoops().size() == 0){ 
                    img.put("booped", "false");
                }
                if(user == null){
                    img.put("booped", "false");
                }else{
                    List <Boop> boops = i.getBoops();
                    for( Boop b : boops){
                        if(b.getUser().equals(user)){
                            img.put("booped", "true");
                        }
                        else{
                            img.put("booped", "false");
                        }
                    }
                }

                

                img.put("id",i.getId());
                img.put("url",i.getUrl());
                // img.put("totalBoops", total);
                jsonImages.put(img);
            }

            

            JSONObject jsondog = new JSONObject();
            jsondog.put("id", dog.getId());
            jsondog.put("name", dog.getName());
            jsondog.put("bio",dog.getBio());
            jsondog.put("breed",breeds);
            jsondog.put("owner",owner);
            jsondog.put("sex",dog.getSex());
            jsondog.put("lat",dog.getLat());
            jsondog.put("lon",dog.getLon());
            jsondog.put("images", jsonImages);
            jsondog.put("totalBoops", allBoops);
            jsondog.put("totalDogs", totalDogs);
  
        return jsondog.toString();
    }

    /**
     * 
     * @param dog dog bound to the form though thymeleaf
     * @param uploadedFile uploaded image for the dog initially, can be empty
     * @return JSONObject with a message or error depending on success
     * @throws JSONException
     */
    @RequestMapping(value="/api/dogs/add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String postNewDog(
            @ModelAttribute Dog dog, @RequestParam(name = "file") MultipartFile uploadedFile) throws JSONException{
            try{
                Validator validator = vFactory.getValidator();

                User user = userService.getCurrentUser();

                //stop if the user is not logged in 
                if(user == null){
                    JSONObject response = new JSONObject();
                    response.put("error","You are not logged in!");
                    return response.toString();
                }

                dog.setOwner(user);

                //if there is not image uploaded with the dog skip this block
                if(!uploadedFile.isEmpty()){
                    dogDao.save(dog);
                    Image image = FileUtil.uploadImage(uploadedFile, user, dog);
                    List<Image> images = dog.getImages();
                    if(images == null){
                        images = new ArrayList<Image>();
                    }
                    images.add(image);
                    dog.setImages(images); 
                }

                Set<ConstraintViolation<Dog>> violations = validator.validate(dog);

                if(!violations.isEmpty()){
                    JSONObject response = new JSONObject();
                    JSONArray errs = new JSONArray();

                    for (ConstraintViolation<Dog> violation : violations) {
                        JSONObject err = new JSONObject();
                        err.put("error",violation.getMessage());
                        errs.put(err);
                    }
                    response.put("errors",errs);
                    return errs.toString();
                }
                
                dogDao.save(dog); 
            }catch(Exception e){
                e.printStackTrace();
                JSONObject response = new JSONObject();
                response.put("error",e.toString());
                return response.toString();
            }  
            JSONObject response = new JSONObject();
            response.put("message","Pup Posted!");
            return response.toString();
    }

    /**
     * 
     * @param dogUpdate The new dog from the bound form on thymeleaf
     * @param id the id of the dog you are going to change
     * @return JSONObject with a message or error depending on success
     * @throws JSONException
     */
    @RequestMapping(value="/api/dogs/{id}/edit", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String editDog(@ModelAttribute Dog dogUpdate, @PathVariable Long id) throws JSONException{
            try{
                Validator validator = vFactory.getValidator();
                User user = userService.getCurrentUser();
                Dog dog = dogDao.getOne(id);

                if(dog.getOwner().equals(user) || userService.userIsRole(user, "admin")){

                    //change attributes of the dog
                    dog.setBio(dogUpdate.getBio());
                    dog.setName(dogUpdate.getName());
                    dog.setBreeds(dogUpdate.getBreeds());
                    dog.setSex(dogUpdate.getSex());
                    dog.setLon(dogUpdate.getLon());
                    dog.setLat(dogUpdate.getLat());

                    Set<ConstraintViolation<Dog>> violations = validator.validate(dog);

                    if(!violations.isEmpty()){
                        JSONObject response = new JSONObject();
                        JSONArray errs = new JSONArray();
    
                        for (ConstraintViolation<Dog> violation : violations) {
                            JSONObject err = new JSONObject();
                            err.put("error",violation.getMessage());
                            errs.put(err);
                        }
                        response.put("errors",errs);
                        return errs.toString();
                    }

                    dogDao.save(dog);
                    JSONObject response = new JSONObject();
                    response.put("message","Pup Edited!");
                    return response.toString();
                }

                JSONObject response = new JSONObject();
                response.put("error","You do not own this dog!");
                return response.toString();
                 
            }catch(Exception e){
                e.printStackTrace();
                JSONObject response = new JSONObject();
                response.put("error",e.toString());
                return response.toString();
            }  
            
        }

    /**
     * 
     * @param id ID of the dog you are requesting to delete
     * @return JSONObject a message or error depdning on the success of the operation
     * @throws JSONException
     */
    @RequestMapping(value="/api/dogs/{id}/delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String deleteDog(@PathVariable Long id) throws JSONException{
            try{
                User user = userService.getCurrentUser();
                Dog dog = dogDao.getOne(id);
                if(dog.getOwner().equals(user) || userService.userIsRole(user, "admin")){
                    
                    dogDao.delete(dog);

                    JSONObject response = new JSONObject();
                    response.put("message","Pup Deleted!");
                    return response.toString();
                }

                JSONObject response = new JSONObject();
                response.put("admin","You do not own this dog!");
                return response.toString();
                
            }catch(Exception e){
                e.printStackTrace();
                JSONObject response = new JSONObject();
                response.put("error",e.toString());
                return response.toString();
            }  
            
        }

    

    

}
