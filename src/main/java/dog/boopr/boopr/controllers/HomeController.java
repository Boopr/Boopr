package dog.boopr.boopr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(
        Model model
    ){
        return "index";
    }

    @GetMapping("/main")
    public String home(
        Model model
    ){
        return "main";
    }
    
}
