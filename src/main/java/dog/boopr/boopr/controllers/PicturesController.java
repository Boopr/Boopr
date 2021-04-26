package dog.boopr.boopr.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dog.boopr.boopr.models.Boop;
import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.models.Image;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.DogRepository;
import dog.boopr.boopr.repositories.ImageRepository;
import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.services.UserServices;
import dog.boopr.boopr.utils.FileUtil;

@RestController
public class PicturesController {

    @Autowired
    private DogRepository dogDao;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private ImageRepository imageDao;


    @Autowired
    private UserServices userService;


    @RequestMapping(value="/api/pics/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getPicsByPictureID(@PathVariable Long id) throws JSONException{


        // JSONArray pics = new JSONArray();


        Image i = imageDao.getOne(id);

            JSONObject boop = new JSONObject();
            JSONArray boops = new JSONArray();
            for(Boop b : i.getBoops()){
                boop.put("id",b.getId());
                boop.put("userId",b.getUser().getId());
                boops.put(boop);
            }

            JSONObject picture = new JSONObject();
            picture.put("id", i.getId());
            picture.put("url", i.getUrl());
            picture.put("boops",boops);
            picture.put("dog_id",i.getDog().getId());
            picture.put("user_id",i.getUser().getId());
            // pics.put(picture);
        
        return picture.toString();
    }
    

    /**
     * 
     * @param id The user's ID you are requesting information on
     * @return A json array with all the images and subseqent information on
     * @throws JSONException
     */
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

    /**
     * 
     * @param id The dogs's ID you are requesting information on
     * @return A json array with all the images and subseqent information on
     * @throws JSONException
     */
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

    /**
     * 
     * @param id The ID of the dog you are submitting information on
     * @param uploadedFile an image file you are going to upload to the website
     * @return Json object with message attached if the operation was successful
     * @throws JSONException
     */
    @RequestMapping(value="/api/dogs/{id}/pics", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public String postNewPicture(@PathVariable Long id, @RequestParam(name = "file") MultipartFile uploadedFile) throws JSONException{

        try{
            //get the current users information
            User user = userService.getCurrentUser();
            Dog dog = dogDao.getOne(id);

            //if user is logged in or is admin            
            if( 
                (!uploadedFile.isEmpty() && user != null) ||
                (!uploadedFile.isEmpty() && userService.userIsRole(user, "admin"))
                ){
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
            JSONObject response = new JSONObject();
            response.put("error",e.toString());
            return response.toString();
        }  
            JSONObject response = new JSONObject();
            response.put("message","Image Deleted!");
            return response.toString();
        }

    /**
     * 
     * @param id the id of the image you are going to delete
     * @return  json object with a message or error depending on success or permissino
     * @throws JSONException
     */
    @RequestMapping(value="/api/dogs/pics/{id}/delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public String deletePicture(
        @PathVariable Long id) throws JSONException{
            try{
                User user = userService.getCurrentUser();
                //get the image
                Image image = imageDao.getOne(id);
                //get the dog the picture belongs to
                Dog dog = dogDao.getOne(image.getDog().getId());


                //if you own the dog or if youre an admin you can delete the picture
                if(dog.getOwner().equals(user) || userService.userIsRole(user, "admin")){

                    imageDao.delete(image);
                    JSONObject response = new JSONObject();
                    response.put("message","Image Deleted!");
                    return response.toString();
                }

                JSONObject response = new JSONObject();
                response.put("error","You do not have permissions!");
                return response.toString();
                
            }catch(Exception e){
                e.printStackTrace();
                JSONObject response = new JSONObject();
                response.put("error",e.toString());
                return response.toString();
            }  
            
        }

}
