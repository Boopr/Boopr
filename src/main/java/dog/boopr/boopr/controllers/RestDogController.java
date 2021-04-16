package dog.boopr.boopr.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
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
import dog.boopr.boopr.repositories.BreedRespository;
import dog.boopr.boopr.repositories.DogRepository;
import dog.boopr.boopr.repositories.ImageRepository;
import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.utils.FileUtil;

@RestController
public class RestDogController {
    
    @Autowired
    private BreedRespository breedDao;

    @Autowired
    private DogRepository dogDao;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private ImageRepository imageDao;

    @Autowired
    private BoopRepository boopDao;

    @Value("$(mapbox_key")
    private String mapboxApiKey;


    @RequestMapping(path = "/keys.js", produces ="application/javascript")
    @ResponseBody
    public String apiKey(){
        return "const apiKey = `"+mapboxApiKey+"`";
    }


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

    @RequestMapping(value="/api/owner/{id}/pics", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getPicsByOwnerID(@PathVariable Long id) throws JSONException{


        JSONArray pics = new JSONArray();


        List<Image> images = imageDao.findAllByUser(userDao.findByIdEquals(id).get(0));

        for( Image i : images){

            JSONObject boops = new JSONObject();
            for(Boop b : i.getBoops()){
                boops.put("id",b.getId());
                boops.put("userId",b.getUser().getId());
            }

            JSONObject picture = new JSONObject();
            picture.put("id", i.getId());
            picture.put("url", i.getUrl());
            picture.put("boops",boops);
            picture.put("dog_id",i.getDog().getId());
            picture.put("user_id",i.getUser().getId());
            pics.put(picture);

        }
        
        return pics.toString();
    }

    @RequestMapping(value="/api/dogs/{id}/pics", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getPicsByDogID(@PathVariable Long id) throws JSONException{


        JSONArray pics = new JSONArray();


        List<Image> images = imageDao.findAllByDog(dogDao.getOne(id));

        for( Image i : images){

            JSONObject boops = new JSONObject();
            for(Boop b : i.getBoops()){
                boops.put("id",b.getId());
                boops.put("userId",b.getUser().getId());
            }

            JSONObject picture = new JSONObject();
            picture.put("id", i.getId());
            picture.put("url", i.getUrl());
            picture.put("boops",boops);
            picture.put("dog_id",i.getDog().getId());
            picture.put("user_id",i.getUser().getId());
            pics.put(picture);

        }
        
        return pics.toString();
    }

    @RequestMapping(value="/api/user/add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String postNewUser(@ModelAttribute User user){
            try{
                userDao.save(user);
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }  
            return "{ 'message': 'Welcome to Boopr!!' }"; 
        }

    @RequestMapping(value="/api/user/edit", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String editUser(@ModelAttribute User userUpdate){
            try{
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                userUpdate.setId(user.getId());
                userDao.save(userUpdate); 
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }  
            return "{ 'message': 'User Edited!' }"; 
    }

    @RequestMapping(value="/api/user/delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String deleteUser(){
            try{
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                userDao.delete(user);
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }  
            return "{ 'message': 'User deleted, Sorry to see you go!' }"; 
        }



    @RequestMapping(value="/api/dogs/add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String postNewDog(@ModelAttribute Dog dog, @RequestParam(name = "file") MultipartFile uploadedFile){
            try{
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    @RequestMapping(value="/api/dogs/{id}/pics", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public String postNewPicture(@PathVariable Long id, @RequestParam(name = "file") MultipartFile uploadedFile){
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Dog dog = dogDao.getOne(id);

            if(!uploadedFile.isEmpty()){
                //we save the dog just in case for any potential errors
                dogDao.save(dog);
                //using our file util we give it the file and user to create the image and write the image to disk
                Image image = FileUtil.uploadImage(uploadedFile, user, dog);
                //grab the list of existing images
                List<Image> images = dog.getImages();
                //check if its empty or the first image
                if(images == null){
                    images = new ArrayList<Image>();
                }
                //add the image to the list
                images.add(image);
                //and then save the list to the dog
                dog.setImages(images); 
            }
            //and finally save the dog 🐶
            dogDao.save(dog);
            
        }catch(Exception e){
            e.printStackTrace();
            return " { 'error' : '" + e.toString() + " ' }";
        }  
        return "{ 'message': 'Image Posted!' }"; 
        }

    @RequestMapping(value="/api/dogs/pics/{id}/delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public String deletePicture(@PathVariable Long id){
        try{
            Image image = imageDao.getOne(id);
            imageDao.delete(image);
        }catch(Exception e){
            e.printStackTrace();
            return " { 'error' : '" + e.toString() + " ' }";
        }  
        return "{ 'message': 'Image Deleted!' }"; 
        }

    @RequestMapping(value="/api/pics/{id}/boop", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String postNewBoop(@PathVariable Long id){
            try{
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Boop boop = new Boop();
                boop.setImage(imageDao.getOne(id));
                boop.setUser(user);
                boopDao.save(boop);
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }  
            return "{ 'message': 'Booped!' }"; 
            }

    @RequestMapping(value="/api/pics/{id}/boop/delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String deleteBoop(@PathVariable Long id){
            try{
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Image image = imageDao.getOne(id);
                Boop boop = null;
                List<Boop> boops = boopDao.findByImageId(image);
                for(Boop b: boops ){
                    if(b.getUser().equals(user)){
                        boop = b;
                    }
                }
                if(boop==null){
                    return "{ 'message': 'Boop doesn't exist!' }";
                }
                boopDao.delete(boop);
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }  
            return "{ 'message': 'De-Booped!' }"; 
            }


    @RequestMapping(value="/api/breed/delete/{id}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public String deleteBreed(
        @PathVariable Long id){
            try{
                Breed breed = breedDao.getOne(id);
                breedDao.delete(breed);
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }
            return "{ 'message': 'Deleted Breed!' }";       
        }


}
