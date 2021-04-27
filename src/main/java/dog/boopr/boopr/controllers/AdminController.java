package dog.boopr.boopr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    @GetMapping("/admin/breeds")
    public String breedEditor(){
        return "admin/breeds";
    }

    @GetMapping("/admin/dogs")
    public String dogEditor(){
        return "admin/dogs";
    }

    @GetMapping("/admin/users")
    public String userEditor(){
        return "admin/users";
    }

    
    @GetMapping("/user/edit/{id}")
    public String userEditorForm(
        @PathVariable Long id,
        Model model
    ){
        model.addAttribute("id", id);
        return "user/edit";
    }
    
    @GetMapping("/dog/add")
    public String register(){
        return "dog/add";
    }

}
