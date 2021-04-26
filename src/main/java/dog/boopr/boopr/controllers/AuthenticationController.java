package dog.boopr.boopr.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import dog.boopr.boopr.models.User;
import dog.boopr.boopr.models.AuthGroup;
import dog.boopr.boopr.repositories.AuthGroupRepository;
import dog.boopr.boopr.repositories.UserRepository;

@Controller
public class AuthenticationController {

    @Autowired
    ValidatorFactory vFactory;

    @Autowired
    UserRepository userDao;
 
    @Autowired
    AuthGroupRepository authGroupDao;
    
    @GetMapping("/login") 
    public String indexPage(){
        return "user/login";
    }

    @GetMapping("/register") 
    public String registerForm(
        Model model
    ){

        model.addAttribute("User",new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String registerHandler(
        Model model,
        User newUser
    ){
 
        Validator validator = vFactory.getValidator();

        Set<ConstraintViolation<User>> violations = validator.validate(newUser);

        if(!violations.isEmpty()){
            List<String> errors = new ArrayList<String>();

            for( ConstraintViolation<User> v : violations){

                errors.add(v.getMessage());
                
            }
            
            model.addAttribute("errors", errors);
            model.addAttribute("User", newUser);
            return "user/register";
        }

        //init the bcrypt encoder 
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(11);
        //and hash the passsword
        String hash = bcrypt.encode(newUser.getPassword());
        //put that new password back into the user object
        newUser.setPassword(hash);
        //save that object to the database
        authGroupDao.save(new AuthGroup(newUser.getUsername(), "USER"));
        userDao.save(newUser);
        //return success!
        return "redirect:/login?success";

    }

}
