package dog.boopr.boopr.controllers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dog.boopr.boopr.models.Boop;
import dog.boopr.boopr.models.Image;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.BoopRepository;
import dog.boopr.boopr.repositories.ImageRepository;
import dog.boopr.boopr.services.UserServices;

@RestController
public class BoopController {

    @Autowired
    private BoopRepository boopDao;

    @Autowired
    private UserServices userService;

    @Autowired
    private ImageRepository imageDao;


    @RequestMapping(value="/api/pics/{id}/boop", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public String postNewBoop(@PathVariable Long id){
        try{
            User user = userService.getCurrentUser();
            List<Boop> imageBoops = boopDao.findAllByImage(imageDao.getOne(id));
            System.out.println(imageBoops.size());
            for(Boop b: imageBoops){
                if(b.getUser().equals(user)){
                    System.out.println("This means its caught");
                    return "{ 'message': 'You've already Booped this Pup's Pic!' }";
                }
            }
            Boop boop = new Boop();
            boop.setImage(imageDao.getOne(id));
            boop.setUser(user);
            boopDao.save(boop);

            Image i = imageDao.getOne(id);

            JSONObject boopObj = new JSONObject();
            JSONArray boops = new JSONArray();
            Long total = 0L;
            for(Boop b : i.getBoops()){
                boopObj.put("id",b.getId());
                boopObj.put("userId",b.getUser().getId());
                boops.put(boop);
                total ++;
            }
            boops.put(total);
            JSONObject picture = new JSONObject();
            picture.put("total", total);
            picture.put("boops",boops);
        
            return picture.toString();
        }catch(Exception e){
            e.printStackTrace();
            return " { 'error' : '" + e.toString() + " ' }";
        }  
        }

    @RequestMapping(value="/api/pics/{id}/boop/delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String deleteBoop(@PathVariable Long id){
            try{
                User user = userService.getCurrentUser();
                Boop boop = null;
                List<Boop> boops = boopDao.findAllByImage(imageDao.getOne(id));
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
    
}
