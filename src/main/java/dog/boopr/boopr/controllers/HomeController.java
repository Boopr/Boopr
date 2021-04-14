package dog.boopr.boopr.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.repositories.DogRepository;

@Controller
public class HomeController {

    @Autowired
    private DogRepository dogDao;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/home")
    public String homePage(Model model) {

        //we're pull from the dog repo
        List<Dog> dogs = dogDao.findAll();
        //and pushing to the frontend
        model.addAttribute("dogs", dogs);
        return "/main";
    }

}
