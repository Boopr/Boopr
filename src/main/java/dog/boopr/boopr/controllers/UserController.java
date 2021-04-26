package dog.boopr.boopr.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dog.boopr.boopr.models.AuthGroup;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.AuthGroupRepository;
import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.services.UserServices;


@RestController
public class UserController {

    @Autowired
    private UserRepository userDao;

    @Autowired
    private UserServices userService;

    @Autowired
    private AuthGroupRepository authGroupRepository;

    @Autowired
    private UserRepository userRepository;

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

    @RequestMapping(value="/api/user/delete/{id}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
        public String deleteUser(@PathVariable Long id){
            try{
                User user = userDao.getOne(id);
                userDao.delete(user);
            }catch(Exception e){
                e.printStackTrace();
                return " { 'error' : '" + e.toString() + " ' }";
            }  
            return "{ 'message': 'User deleted, Sorry to see you go!' }"; 
        }


    @PostMapping("/api/register")
    public String register(
        User user
    ) throws JSONException{

        //regex for email check
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);


        Matcher matcher = pattern.matcher(user.getEmail());
        
        if(!matcher.matches()){
            JSONObject response = new JSONObject();
            response.put("error","Invalid email address!");
            return response.toString();
        }

        if(user.getUsername().isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error","Please enter a username!");
            return response.toString();
        }

        if(user.getUsername().length() < 6){
            JSONObject response = new JSONObject();
            response.put("error","Username must contain 6 characters or more!");
            return response.toString();
        }

        if(user.getPassword().isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error","Please enter a password!");
            return response.toString();
        }

        if(user.getPassword().length() < 6){
            JSONObject response = new JSONObject();
            response.put("error","Password must be longer than 6 characters!");
            return response.toString();
        }

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        String hash = bcrypt.encode(user.getPassword());

        user.setPassword(hash);
        authGroupRepository.save(new AuthGroup(user.getUsername(), "USER"));
        userRepository.save(user);

        JSONObject response = new JSONObject();
        response.put("message","User Registered!");
        return response.toString();

    }
}
