package dog.boopr.boopr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    
    @GetMapping("/login") 
    public String indexPage(){
        return "user/login";
    }

}
