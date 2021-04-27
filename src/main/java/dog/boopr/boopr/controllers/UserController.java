package dog.boopr.boopr.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import dog.boopr.boopr.models.AuthGroup;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.AuthGroupRepository;
import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.services.UserServices;


@RestController
public class UserController {

    
    @Autowired
	private UserServices userDetailsService;

    @Autowired
    private AuthGroupRepository authGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/user/edit")
    public String userSelfEdit(
        User changedUser
    ) throws JSONException{

        User user = userDetailsService.getCurrentUser();

        //regex for email check
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(changedUser.getEmail());

        if(!matcher.matches()){
            JSONObject response = new JSONObject();
            response.put("error","Invalid email address!");
            return response.toString();
        }

        if(changedUser.getUsername().isEmpty()){
            JSONObject response = new JSONObject();
            response.put("error","Please enter a username!");
            return response.toString();
        }

        if(changedUser.getUsername().length() < 6){
            JSONObject response = new JSONObject();
            response.put("error","Username must contain 6 characters or more!");
            return response.toString();
        }

        
        //changes username
        user.setUsername(changedUser.getUsername());
        user.setEmail(changedUser.getEmail());

        //Sets hash to password
        if(!changedUser.getPassword().isEmpty()){
    
            if(changedUser.getPassword().length() < 6){
                JSONObject response = new JSONObject();
                response.put("error","Password must be longer than 6 characters!");
                return response.toString();
            }

            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(11);
            String hash = bcrypt.encode(changedUser.getPassword());
            user.setPassword(hash);
        }

        userRepository.save(user);
        
        JSONObject response = new JSONObject();
        response.put("message","User information changed!");
        return response.toString();

    }

    @PostMapping("/api/user/edit/{id}")
    public String userEdit(
        User changedUser,
        @PathVariable Long id
    ) throws JSONException{

        User currentUser = userDetailsService.getCurrentUser();
        User user = userRepository.getOne(id);

        //if the current user is the same as you or you are admin
        if(currentUser.equals(user) || 
        userDetailsService.userIsRole(currentUser, "admin")){

            //regex for email check
            String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(changedUser.getEmail());

            if(!matcher.matches()){
                JSONObject response = new JSONObject();
                response.put("error","Invalid email address!");
                return response.toString();
            }

            if(changedUser.getUsername().isEmpty()){
                JSONObject response = new JSONObject();
                response.put("error","Please enter a username!");
                return response.toString();
            }

            if(changedUser.getUsername().length() < 6){
                JSONObject response = new JSONObject();
                response.put("error","Username must contain 6 characters or more!");
                return response.toString();
            }

            //changes username
            user.setUsername(changedUser.getUsername());
            user.setEmail(changedUser.getEmail());

            //Sets hash to password
            if(!changedUser.getPassword().isEmpty()){

                if(changedUser.getPassword().length() < 6){
                    JSONObject response = new JSONObject();
                    response.put("error","Password must be longer than 6 characters!");
                    return response.toString();
                }

                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(11);
                String hash = bcrypt.encode(changedUser.getPassword());
                user.setPassword(hash);
            }

            userRepository.save(user);
            
            JSONObject response = new JSONObject();
            response.put("message","User information changed!");
            return response.toString();

        }

        JSONObject response = new JSONObject();
        response.put("error","You do not have permissions!");
        return response.toString();

    }

    @DeleteMapping("/api/user/remove/{id}")
    public String deleteUser(
        @PathVariable Long id
    ) throws JSONException{

        User currentUser = userDetailsService.getCurrentUser();
        User user = userRepository.getOne(id);

        //if the current user is the same as you or you are admin
        if(currentUser.equals(user) || 
        userDetailsService.userIsRole(currentUser, "admin")){

            userRepository.delete(user);

            JSONObject response = new JSONObject();
            response.put("message","User deleted!");
            return response.toString();
           
        }
       
        JSONObject response = new JSONObject();
        response.put("error","You do not have permissions!");
        return response.toString();
    }

    @GetMapping("/api/user/{id}")
    public String getUser(
        @PathVariable Long id
    ) throws JSONException{

        User currentUser = userDetailsService.getCurrentUser();
        User user = userRepository.getOne(id);

        //if the current user is the same as you or you are admin
        if(currentUser.equals(user) || 
        userDetailsService.userIsRole(currentUser, "admin")){

            JSONObject response = new JSONObject();
            response.put("username", user.getUsername());
            response.put("id", user.getId());
            response.put("email", user.getEmail());

            return response.toString();
        }
       
        JSONObject response = new JSONObject();
        response.put("error","You do not have permissions!");
        return response.toString();
    }

    @GetMapping("/api/user/all")
    public String getUsers() throws JSONException{

        User currentUser = userDetailsService.getCurrentUser();

        //if the current user is the same as you or you are admin
        if(userDetailsService.userIsRole(currentUser, "admin")){

            List<User> users = userRepository.findAll();

            JSONArray response = new JSONArray();

            for( User u : users){

                JSONObject user = new JSONObject();
                user.put("username", u.getUsername());
                user.put("id", u.getId());
                user.put("email", u.getEmail());
                JSONArray authG = new JSONArray();
                List<AuthGroup> authGroups = authGroupRepository.findByUsername(u.getUsername());
                for(AuthGroup a : authGroups){
                    JSONObject authgroup = new JSONObject();
                    authgroup.put("name", a.getAuthGroup().toLowerCase());
                    authG.put(authgroup);
                }
                user.put("authGroups",authG);

                response.put(user);

            }
            
            return response.toString();
        }
       
        JSONObject response = new JSONObject();
        response.put("error","You do not have permissions!");
        return response.toString();
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
