package dog.boopr.boopr.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dog.boopr.boopr.models.Breed;
import dog.boopr.boopr.models.Dog;
import dog.boopr.boopr.repositories.BreedRespository;
import dog.boopr.boopr.repositories.DogRepository;

@Controller
public class DogController {

    @Autowired
    private DogRepository dogDao;

    @Autowired
    private BreedRespository breedDao;

    @GetMapping("/breed/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String breedScreen(
        Model model
    ){

        List<Breed> breeds =  breedDao.findAll();
        model.addAttribute("breeds", breeds);

        return "breed/edit";
    }
    
    @GetMapping("/editprofile/{id}")
    public String editprofilePage(
        Model model, 
        @PathVariable Long id){
            Dog dog = dogDao.getOne(id);
            long totalDogs = dogDao.findAll().size()-1;
            model.addAttribute("dog", dog);
            model.addAttribute("totalDogs", totalDogs);
            return "dog/editprofile";
    }

    @GetMapping("/dog/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String dogAdd(
        Model model
    ){
        List<Breed> breeds = breedDao.findAll();
        model.addAttribute("breeds", breeds);
        return "dog/edit";
    }

    @GetMapping("/dog/pic/add/{id}")
    public String dogAddpic(
        Model model,
        @PathVariable Long id){
            List<Breed> breeds = breedDao.findAll();
            Dog dog = dogDao.getOne(id);
            model.addAttribute("breeds", breeds);
            model.addAttribute("dog", dog);
            return "dog/image";
    }
    

}


    


