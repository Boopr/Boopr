package dog.boopr.boopr.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dog.boopr.boopr.models.Breed;
import dog.boopr.boopr.repositories.BreedRespository;

@Controller
public class DogController {

    @Autowired
    private BreedRespository breedDao;

    @GetMapping("/breed/edit")
    public String breedScreen(
        Model model
    ){

        List<Breed> breeds =  breedDao.findAll();
        model.addAttribute("breeds", breeds);

        return "breed/edit";
    }


}


    


