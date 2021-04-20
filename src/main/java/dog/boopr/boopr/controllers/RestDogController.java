package dog.boopr.boopr.controllers;

import java.util.ArrayList;
import java.util.List;

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

import dog.boopr.boopr.models.Breed;
import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.models.Image;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.DogRepository;
import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.services.UserServices;
import dog.boopr.boopr.utils.FileUtil;

@RestController
public class RestDogController {

    @Autowired
    private DogRepository dogDao;

    @Autowired
    private UserRepository userDao;

    @Value("${mapbox-key}")
    private String mapboxApiKey;

    @Autowired
    private UserServices userService;


    @RequestMapping(path = "/js/keys.js", produces ="application/javascript")
    @ResponseBody
    public String apiKey(){
        return "const apiKey = `"+mapboxApiKey+"`";
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
            JSONArray images = new JSONArray();
            for(Image i: d.getImages()){
                images.put(i.getUrl());
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
            dog.put("sex",d.getSex());
            dog.put("lat",d.getLat());
            dog.put("lon",d.getLon());


            dogs.put(dog);

        }
        
        return dogs.toString();
    }

    @RequestMapping(value="/api/dogs/owner/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getDogsByOwner(@PathVariable Long id) throws JSONException{


        JSONArray dogs = new JSONArray();

        User user = userDao.findByIdEquals(id).get(0);

        List<Dog> userDogs = user.getDogs();

        for( Dog d : userDogs){

            JSONArray breeds = new JSONArray();
            for(Breed b: d.getBreeds()){
                breeds.put(b.getBreed());
            }
            JSONArray images = new JSONArray();
            for(Image i: d.getImages()){
                images.put(i.getUrl());
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
            dog.put("sex",d.getSex());
            dog.put("lat",d.getLat());
            dog.put("lon",d.getLon());


            dogs.put(dog);

        }
        
        return dogs.toString();
    }

    @RequestMapping(value="/api/dogs/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getDogsByID(@PathVariable Long id) throws JSONException{


        
        long totalDogs = dogDao.findAll().size()-1;
        Dog dog = dogDao.getOne(id);

            JSONArray breeds = new JSONArray();
            for(Breed b: dog.getBreeds()){
                breeds.put(b.getBreed());
            }
            JSONObject owner = new JSONObject();
            owner.put("id",dog.getOwner().getId());
            owner.put("username",dog.getOwner().getUsername());
            owner.put("id",dog.getOwner().getEmail());
            JSONArray jsonImages = new JSONArray();
            List<Image> images = dog.getImages();
            for( Image i : images){
                JSONObject img = new JSONObject();
                img.put("id",i.getId());
                img.put("url",i.getUrl());
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
            jsondog.put("totalDogs", totalDogs);


            

        
        return jsondog.toString();
    }

    

    @RequestMapping(value="/api/dogs/add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String postNewDog(@ModelAttribute Dog dog, @RequestParam(name = "file") MultipartFile uploadedFile){
            try{
                User user = userService.getCurrentUser();
                dog.setOwner(user);
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
                dogDao.save(dog); 
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }  
            return "{ 'message': 'Pup Posted!' }"; 
    }

    @RequestMapping(value="/api/dogs/{id}/edit", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String editDog(@ModelAttribute Dog dogUpdate, @PathVariable Long id){
            try{
                User user = userService.getCurrentUser();
                dogUpdate.setOwner(user);
                dogUpdate.setId(id);
                dogDao.save(dogUpdate); 
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }  
            return "{ 'message': 'Pup Edited!' }"; 
        }

    @RequestMapping(value="/api/dogs/{id}/delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String deleteDog(@PathVariable Long id){
            try{
                Dog dog = dogDao.getOne(id);
                dogDao.delete(dog);
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }  
            return "{ 'message': 'Pup Edited!' }"; 
        }

    

    

}
