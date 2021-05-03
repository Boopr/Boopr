package dog.boopr.boopr.controllers;

import java.util.List;

import dog.boopr.boopr.repositories.UserRepository;
import dog.boopr.boopr.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/")
    public String index(Model model) {

        if(userService.getCurrentUser() == null){
            List<Dog> dog = dogDao.findAll();
            model.addAttribute("dogs", dog);
            return "main";
        }
        return "redirect:/home";
        
    }

    @GetMapping("/home")
    public String homePage(Model model) {

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

    @GetMapping("/user/userprofile/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String userprofileAdminPage(Model model, @PathVariable Long id) {

        List<Dog> dogs = dogDao.findAll();
        User user = userDao.getOne(id);
        model.addAttribute("user", user);
        model.addAttribute("dogs", dogs);
        return "user/userprofile";
    }

    @GetMapping("/profile/{id}")
    public String profilePages() {
        return "dog/profile";
    }

    @GetMapping("/user/profile")
    public String userProfile() {
        return "user/profile";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin() {
        return "admin/admin";
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String userManage(Model model) {
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        return "user/manage";
    }

    @GetMapping("/dog/manage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String dogAdmingManage(Model model) {
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        return "dog/manageDogs";
    }

}
