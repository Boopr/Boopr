package dog.boopr.boopr.controllers;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.services.UserServices;


@RestController
public class UserController {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private UserServices userService;

    @RequestMapping(value="/api/users", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public String getUsers() throws JSONException{


        JSONArray users = new JSONArray();

        List<User> userData = userDao.findAll();

        for( User u : userData){

            JSONObject user = new JSONObject();
            user.put("id",u.getId());
            user.put("username",u.getUsername());
            user.put("email",u.getEmail());


            users.put(user);

        }
        
        return users.toString();
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
                User user = userService.getCurrentUser();
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
                User user = userService.getCurrentUser();
                userDao.delete(user);
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }  
            return "{ 'message': 'User deleted, Sorry to see you go!' }"; 
        }

}
