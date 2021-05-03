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
            if(user == null){
                JSONObject response = new JSONObject();
                response.put("error","You are not logged in!");
                return response.toString();
            }

            List<Boop> imageBoops = boopDao.findAllByImage(imageDao.getOne(id));

            for(int i = 0; i < imageBoops.size();i++){

                if(imageBoops.get(i).getUser().equals(user)){

                    boopDao.delete(imageBoops.get(i));
                    
                    JSONObject response = new JSONObject();
                    response.put("message","Boop removed!");
                    response.put("totalBoops",imageBoops.size()-1);
                    return response.toString();
                }

            }

            Boop boop = new Boop();
            boop.setImage(imageDao.getOne(id));
            boop.setUser(user);
            boopDao.save(boop);

            JSONObject response = new JSONObject();
            response.put("totalBoops", imageBoops.size()+1);
            response.put("message","Booped!");
        
            return response.toString();
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
