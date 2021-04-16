package dog.boopr.boopr.controllers;

import java.util.List;

import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.models.User;
import dog.boopr.boopr.repositories.DogRepository;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    private DogRepository dogDao;

    @Autowired
    private UserServices userService;

    @GetMapping("/")
    public String index(Model model) {

        if(userService.getCurrentUser() == null){
            return "index";
        }
        return "redirect:/home";
        
    }

    @GetMapping("/home")
    public String homePage(Model model) {

        //we're pull from the dog repo
        List<Dog> dogs = dogDao.findAll();
        User user = userService.getCurrentUser();
        //and pushing to the frontend
        model.addAttribute("user", user);
        model.addAttribute("dogs", dogs);
        return "home";
    }

    @GetMapping("/profile/{id}")
    public String profilePage(Model model, @PathVariable Long id) {
        Dog dog = dogDao.getOne(id);
        long totalDogs = dogDao.findAll().size()-1;
        model.addAttribute("dog", dog);
        model.addAttribute("totalDogs", totalDogs);
        return "user/profile";
    }
}
