package dog.boopr.boopr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/breeds")
    public String breedEditor(){
        return "admin/breeds";
    }

}
