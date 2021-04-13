package dog.boopr.boopr.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.UserRepository;

@Controller
public class AuthenticationController {

    @Autowired
    UserRepository userDao;
    
    @GetMapping("/login") 
    public String indexPage(){
        return "user/login";
    }

    @GetMapping("/register") 
    public String registerForm(
        Model model
    ){

        model.addAttribute(new User());
        return "user/registration";
    }

    @PostMapping("/register")
    public String registerHandler(
        
        Model model,
        @Valid User newUser,
        BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "register";
        }

        //init the bcrypt encoder 
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(11);
        //and hash the passsword
        String hash = bcrypt.encode(newUser.getPassword());
        //put that new password back into the user object
        newUser.setPassword(hash);
        //save that object to the database
        userDao.save(newUser);
        //return success!
        return "redirect:/login?success";

    }

}
