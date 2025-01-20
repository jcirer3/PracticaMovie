package com.esliceu.movie.controllers;

import com.esliceu.movie.models.Person;
import com.esliceu.movie.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {
    @Autowired
    PersonService personService;

    @GetMapping("/persons")
    public String listPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        Page<Person> personPage = personService.getPaginatedPersons(page, size);
        model.addAttribute("persons", personPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", personPage.getTotalPages());
        model.addAttribute("totalItems", personPage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "persons";
    }

    @PostMapping("/add-person")
    public String newPerson(@RequestParam String personName) {
        personService.savePerson(personName);
        return "redirect:/persons";
    }

    @PostMapping("/delete-person")
    public String deletePerson(@RequestParam Integer personId) {
        personService.deletePerson(personId);
        return "redirect:/persons";
    }

    @GetMapping("/update-person")
    public String showUpdateForm(@RequestParam Integer personId, Model model) {
        Person person = personService.getPersonById(personId);
        model.addAttribute("person", person);
        return "updateperson";
    }

    @PostMapping("/update-person")
    public String updatePerson(@RequestParam Integer personId, @RequestParam String personName, Model model) {
        String trimmedName = personName.trim();
        if (trimmedName.isEmpty() || personName.startsWith(" ") || personName.endsWith(" ")) {
            model.addAttribute("error", "Name cannot be empty, or have leading/trailing spaces.");
            Person person = personService.getPersonById(personId);
            model.addAttribute("person", person);
            return "updateperson";
        }
        personService.updatePersonNameById(personId, personName);
        return "redirect:/persons";
    }

}
