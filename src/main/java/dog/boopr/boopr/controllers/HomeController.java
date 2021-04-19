package dog.boopr.boopr.controllers;

import java.util.List;

import dog.boopr.boopr.models.AuthGroup;
import dog.boopr.boopr.repositories.AuthGroupRepository;
import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository userDao;

    @Autowired
    private UserServices userService;

    @Autowired
    private AuthGroupRepository authGroupDao;

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

    @GetMapping("/user/userprofile")
    public String userprofilePage(Model model) {

        List<Dog> dogs = dogDao.findAll();
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("dogs", dogs);
        return "user/userprofile";
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
    public String profilePage(Model model, @PathVariable Long id) {
        Dog dog = dogDao.getOne(id);
        long totalDogs = dogDao.findAll().size()-1;
        model.addAttribute("dog", dog);
        model.addAttribute("totalDogs", totalDogs);
        return "user/profile";
    }

    @GetMapping("/user/manage")
    public String userManage(Model model) {
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        return "user/manage";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, String authGroup) {
        List<AuthGroup> authGroups = authGroupDao.findByUsername(authGroup);
        model.addAttribute("authGroups", authGroups);
        return "/admin";
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<Dog> dog = dogDao.findAll();
        model.addAttribute("dogs", dog);
        return "/main";
    }
}
