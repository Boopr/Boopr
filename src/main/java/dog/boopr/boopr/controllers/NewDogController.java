package dog.boopr.boopr.controllers;

import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import dog.boopr.boopr.models.Breed;
import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.models.Image;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.BreedRespository;
import dog.boopr.boopr.repositories.DogRepository;
import dog.boopr.boopr.repositories.ImageRepository;
import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.services.UserServices;
import dog.boopr.boopr.utils.FileUtil;

@RestController
public class NewDogController {
    @Autowired
	private UserServices userDetailsService;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private BreedRespository breedRespository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    // @GetMapping("/api/dog/edit/{id}")
    // public String getDog(
    //     @PathVariable Long id
    // ) throws JSONException{

    //     Dog dog = dogRepository.getOne(id);

    //     JSONObject response = new JSONObject();
    //     response.put("id", dog.getId());
    //     response.put("name", dog.getName());
    //     response.put("bio", dog.getBio());
    //     response.put("sex", dog.getSex());

    //     JSONArray cords = new JSONArray();
    //     cords.put(dog.getLon());
    //     cords.put(dog.getLat());

    //     response.put("coordinates",cords);

    //     JSONArray breeds = new JSONArray();
    //     for( Breed b : dog.getBreeds()){
    //         JSONObject breed = new JSONObject();
    //         breed.put("id", b.getId());
    //         breed.put("name", b.getBreed());
    //         breeds.put(breeds);
    //     }

    //     response.put("breeds", breeds);
        
    //     JSONArray images = new JSONArray();
    //     int totalBoops;
    //     for( Image i : dog.getImages()){
            
    //         JSONObject image = new JSONObject();
    //         image.put("id", i.getId());
    //         image.put("url", i.getUrl());
    //         image.put("boops", i.getBoops().size());
    //         images.put(image);
    //         totalBoops += i.getBoops().size();
    //     }

    //     response.put("images", images);
    //     response.put("owner", dog.getOwner().getUsername());

    //     return response.toString();
    // }


    // @GetMapping("/api/dog/new/all")
    // public String getAllDogs() throws JSONException{

    //     JSONArray response = new JSONArray();

    //     for( Dog d : dogRepository.findAll()){

    //         JSONObject dog = new JSONObject();
    //         dog.put("id", d.getId());
    //         dog.put("name", d.getName());
    //         dog.put("bio", d.getBio());
    //         dog.put("sex", d.getSex());
    
    //         JSONArray cords = new JSONArray();
    //         cords.put(d.getLon());
    //         cords.put(d.getLat()));
    
    //         dog.put("coordinates",cords);
    
    //         JSONArray breeds = new JSONArray();
    //         for( Breed b : d.getBreeds()){
    //             JSONObject breed = new JSONObject();
    //             breed.put("id", b.getId());
    //             breed.put("name", b.getBreed());
    //             breeds.put(breed);
    //         }
    
    //         dog.put("breeds", breeds);
    
    //         JSONArray images = new JSONArray();
    //         int totalBoops;
    //         for( Image i : d.getImages()){
    //             JSONObject image = new JSONObject();
    //             image.put("id", i.getId());
    //             image.put("url", i.getUrl());
    //             image.put("boops", i.getBoops().size());
    //             totalBoops += i.getBoops().size();
    //             images.put(image);
    //         }
    //         dog.put("totalBoops", totalBoops);
    //         dog.put("images", images);
    //         dog.put("owner", d.getOwner().getUsername());

    //         response.put(dog);

    //     }

    //     return response.toString();
    // }

    // @GetMapping("/api/dog/self")
    // public String getMyDogs() throws JSONException{

    //     User user = userDetailsService.getCurrentUser();
    
    //     JSONArray response = new JSONArray();

    //     for( Dog d : dogRepository.findAllByOwner(user)){

    //         JSONObject dog = new JSONObject();
    //         dog.put("name", d.getName());
    //         dog.put("bio", d.getBio());
    //         dog.put("sex", d.getSex());
    
    //         JSONArray cords = new JSONArray();
    //         cords.put(d.getLon());
    //         cords.put(d.getLat()));
    
    //         dog.put("coordinates",cords);
    
    //         JSONArray breeds = new JSONArray();
    //         for( Breed b : d.getBreeds()){
    //             JSONObject breed = new JSONObject();
    //             breed.put("id", b.getId());
    //             breed.put("name", b.getBreed());
    //             breeds.put(breed);
    //         }
    
    //         dog.put("breeds", breeds);
    
    //         JSONArray images = new JSONArray();
    //         for( Image i : d.getImages()){
    //             JSONObject image = new JSONObject();
    //             image.put("id", i.getId());
    //             image.put("url", i.getUrl());
    //             images.put(image);
    //         }
    
    //         dog.put("images", images);
    //         dog.put("owner", d.getOwner());

    //         response.put(dog);

    //     }

    //     return response.toString();
    // }

    // @GetMapping("/api/dog/new/owner/{id}")
    // public String getDogsByUser(
    //     @PathVariable Long id
    // ) throws JSONException{

    //     User user = userRepository.getOne(id);
    
    //     JSONArray response = new JSONArray();

    //     for( Dog d : dogRepository.findAllByOwner(user)){

    //         JSONObject dog = new JSONObject();
    //         dog.put("name", d.getName());
    //         dog.put("bio", d.getBio());
    //         dog.put("sex", d.getSex());
    
    //         JSONArray cords = new JSONArray();
    //         cords.put(d.getLon());
    //         cords.put(d.getLat()));
    
    //         dog.put("coordinates",cords);
    
    //         JSONArray breeds = new JSONArray();
    //         for( Breed b : d.getBreeds()){
    //             JSONObject breed = new JSONObject();
    //             breed.put("id", b.getId());
    //             breed.put("name", b.getBreed());
    //             breeds.put(breed);
    //         }
    
    //         dog.put("breeds", breeds);
    
    //         JSONArray images = new JSONArray();
    //         for( Image i : d.getImages()){
    //             JSONObject image = new JSONObject();
    //             image.put("id", i.getId());
    //             image.put("url", i.getUrl());
    //             images.put(image);
    //         }
    
    //         dog.put("images", images);
    //         dog.put("owner", d.getOwner());

    //         response.put(dog);

    //     }

    //     return response.toString();
    // }

    @PutMapping("/api/dog/edit/{id}")
    public String editDogById(
        @RequestParam(required = false, name = "name") String name,
        @RequestParam(required = false, name = "sex") String sex,
        @RequestParam(required = false, name = "bio") String bio,
        @RequestParam(required = false, name = "longitude") float longitude,
        @RequestParam(required = false, name = "latitude") float latitude,
        @RequestParam(required = false, name = "breeds") List<Long> breeds,
        @RequestParam(required = false, name = "image") MultipartFile image,
        @PathVariable Long id
    ) throws Exception{

        Dog dog = dogRepository.getOne(id);
        User currentUser = userDetailsService.getCurrentUser();

        //If you are an admin or own the dog
        if(userDetailsService.userIsRole(currentUser, "admin") ||
           dog.getOwner().equals(currentUser)){

            if(name != null){
                dog.setName(name);
            }
    
            if(sex != null){
                if(sex.equals("male")){
                    dog.setSex(true);
                }else{
                    dog.setSex(false);
                }
            }
    
            if(bio != null){
                dog.setBio(bio);
            }
    
            if(longitude != 0.0f){
                dog.setLon(longitude);
            }
    
            if(latitude != 0.0f){
                dog.setLat(latitude);
            }

            if(breeds != null){
                List<Breed> inputBreeds = new ArrayList<>();
                for( Long b : breeds){
                    Breed breed = breedRespository.getOne(b);
                    inputBreeds.add(breed);
                }
                dog.setBreeds(inputBreeds);
            }

            if(!image.isEmpty()){
                dogRepository.save(dog);
                Image i = FileUtil.uploadImage(image, currentUser, dog);
                List<Image> images = dog.getImages();
                images.add(i);
                dog.setImages(images);
            }

            dogRepository.save(dog);
            JSONObject response = new JSONObject();
            response.put("message","Dog Edited!");
            return response.toString();
        }

        JSONObject response = new JSONObject();
        response.put("error","You do not have permissions!");
        return response.toString();
    }

    @PostMapping("/api/dog/add")
    public String addDog(
        @RequestParam(required = false, name = "name") String name,
        @RequestParam(required = false, name = "sex") String sex,
        @RequestParam(required = false, name = "bio") String bio,
        @RequestParam(required = false, name = "longitude") long longitude,
        @RequestParam(required = false, name = "latitude") long latitude,
        @RequestParam(required = false, name = "breeds") List<Long> breeds,
        @RequestParam(required = false, name = "image") MultipartFile image
    ) throws Exception{

        Dog dog = new Dog();
        User currentUser = userDetailsService.getCurrentUser();

        if(currentUser == null){
            JSONObject response = new JSONObject();
            response.put("error","You are not logged in!");
            return response.toString();
        }else{
            dog.setOwner(currentUser);
        }

        //Name check
        if(name.isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error","You need to name your dog!");
            return response.toString();
        }
        dog.setName(name);


        //Sex check
        if(sex.isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error","You need assign a sex to your dog!");
            return response.toString();
        }

        if(sex.equals("male")){
            dog.setSex(true);
        }else{
            dog.setSex(false);
        }

        //Bio check
        if(bio.isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error","You to give your dog a bio!");
            return response.toString();
        }
        dog.setBio(bio);

        //Location check
        if(longitude == 0.0f){
            JSONObject response = new JSONObject();
            response.put("error","You give your dog a location!");
            return response.toString();
        }
        dog.setLon(longitude);
        dog.setLat(latitude);

        //Breed check
        if(breeds.isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error","You assign your dog a breed!");
            return response.toString();
        }
        List<Breed> inputBreeds = new ArrayList<>();
        for( Long b : breeds){
            Breed breed = breedRespository.getOne(b);
            inputBreeds.add(breed);
        }
        dog.setBreeds(inputBreeds);

        //Image check
        if(image.isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error","You need to upload a picture for your dog!");
            return response.toString();
        }

        dogRepository.save(dog);
        Image i = FileUtil.uploadImage(image, currentUser, dog);
        List<Image> images = new ArrayList<>();
        images.add(i);
        dog.setImages(images);

        dogRepository.save(dog);
        JSONObject response = new JSONObject();
        response.put("message","Dog added!");
        return response.toString();
    }

    @DeleteMapping("/api/dog/remove/{id}")
    public String removeDog(
        @RequestParam(required = false, name = "image") MultipartFile image,
        @PathVariable Long id
    ) throws Exception{

        Dog dog = dogRepository.getOne(id);
        User currentUser = userDetailsService.getCurrentUser();

        //If you are an admin or own the dog
        if(userDetailsService.userIsRole(currentUser, "admin") ||
           dog.getOwner().equals(currentUser)){

            dogRepository.delete(dog);

            JSONObject response = new JSONObject();
            response.put("message","Dog Deleted!");
            return response.toString();
        }

        JSONObject response = new JSONObject();
        response.put("error","You do not have permissions!");
        return response.toString();
    }

    @PostMapping("/api/dog/picture/add/{id}")
    public String addDogPicture(
        @RequestParam(required = true, name = "image") MultipartFile image,
        @PathVariable Long id
    ) throws Exception{

        Dog dog = dogRepository.getOne(id);
        User currentUser = userDetailsService.getCurrentUser();

        //If you are an admin or own the dog
        if(userDetailsService.userIsRole(currentUser, "admin") ||
           dog.getOwner().equals(currentUser)){

            if(!image.isEmpty()){
                dogRepository.save(dog);
                Image i = FileUtil.uploadImage(image, currentUser, dog);
                List<Image> images = dog.getImages();
                images.add(i);
                dog.setImages(images);
            }

            JSONObject response = new JSONObject();
            response.put("message","Picture Added!");
            return response.toString();
        }

        JSONObject response = new JSONObject();
        response.put("error","You do not have permissions!");
        return response.toString();
    }

    @DeleteMapping("/api/dog/picture/remove/{id}")
    public String removeDogPicture(
        @PathVariable Long id
    ) throws Exception{

        Image image = imageRepository.getOne(id);
        Dog dog = image.getDog();
        User currentUser = userDetailsService.getCurrentUser();

        //If you are an admin or own the dog
        if(userDetailsService.userIsRole(currentUser, "admin") ||
           dog.getOwner().equals(currentUser)){

            imageRepository.delete(image);

            JSONObject response = new JSONObject();
            response.put("message","Picture Added!");
            return response.toString();
        }

        JSONObject response = new JSONObject();
        response.put("error","You do not have permissions!");
        return response.toString();
    }
}
