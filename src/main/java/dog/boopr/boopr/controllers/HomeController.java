package dog.boopr.boopr.controllers;

import java.util.List;

import dog.boopr.boopr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.repositories.DogRepository;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    private DogRepository dogDao;

    @Autowired
    private UserRepository users;

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
        return "main";
    }

    @GetMapping("/profile/{id}")
    public String profilePage(Model model, @PathVariable String id) {
        Dog dog = dogDao.getOne(Long.parseLong(id));
        model.addAttribute("dog", dog);
        return "user/profile";
    }

    @GetMapping("/location/{id}")
    public String locationPage(Model model, @PathVariable String id) {
        Dog dog = dogDao.getOne(Long.parseLong(id));
        model.addAttribute("dog", dog);
        return "user/map-location";
    }
}
