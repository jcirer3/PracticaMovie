package com.esliceu.movie.controllers;

import com.esliceu.movie.models.Person;
import com.esliceu.movie.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PersonController {
    @Autowired
    PersonService personService;

    @GetMapping("/persons")
    public String allPersons (Model model){
        List<Person> persons = personService.getPersons();
        //model.addAttribute("persons", persons);
        return "persons";
    }

    @PostMapping("/persons")
    public String newPerson (@RequestParam String personName){
        personService.savePerson(personName);
        return "redirect:/persons";
    }

}
