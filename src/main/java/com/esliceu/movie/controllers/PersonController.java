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

import java.util.List;

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

        String jsonToSend = personService.getPersonJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("persons", personPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", personPage.getTotalPages());
        model.addAttribute("totalItems", personPage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "persons";
    }

    @PostMapping("/persons")
    public String searchPeron(@RequestParam String personName, Model model){
        List<Person> persons = personService.findByName(personName);
        model.addAttribute("persons", persons);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", persons.size());
        return "persons";
    }

    @PostMapping("/add-person")
    public String newPerson(@RequestParam String personName, Model model) {
        String message = personService.savePerson(personName);

        if (message != null){
            List<Person> persons = personService.findByName(personName);
            model.addAttribute("persons", persons);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", persons.size());
            model.addAttribute("message", message);
            return "persons";
        }

        List<Person> persons = personService.findByName(personName);
        model.addAttribute("persons", persons);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", persons.size());
        model.addAttribute("message", "El usuario ha sido creado correctamente.");
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
        String message = personService.updatePersonNameById(personId, personName);
        System.out.println(message);
        if (message != null) {
            model.addAttribute("error", message);
            Person person = personService.getPersonById(personId);
            model.addAttribute("person", person);
            return "updateperson";
        }
        model.addAttribute("error", "El usuario ha sido modificado con éxito.");
        Person person = personService.getPersonById(personId);
        model.addAttribute("person", person);
        return "updateperson";
    }
}
