package com.esliceu.movie.controllers;

import com.esliceu.movie.models.Gender;
import com.esliceu.movie.services.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GenderController {
    @Autowired
    GenderService genderService;

    @GetMapping("/genders")
    public String listGenders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        Page<Gender> genderPage = genderService.getPaginatedGenders(page, size);

        String jsonToSend = genderService.getGenderJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("genders", genderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", genderPage.getTotalPages());
        model.addAttribute("totalItems", genderPage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "genders";
    }

    @PostMapping("/genders")
    public String searchGender(@RequestParam String gender, Model model){
        List<Gender> genders = genderService.findByGender(gender);
        model.addAttribute("genders", genders);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", genders.size());
        return "genders";
    }

    @PostMapping("/add-gender")
    public String newGender(@RequestParam String gender, Model model) {
        String message = genderService.saveGender(gender);

        if (message != null){
            List<Gender> genders = genderService.findByGender(gender);
            model.addAttribute("genders", genders);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", genders.size());
            model.addAttribute("message", message);
            return "genders";
        }

        List<Gender> genders = genderService.findByGender(gender);
        model.addAttribute("genders", genders);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", genders.size());
        model.addAttribute("message", "El género ha sido creado correctamente.");
        return "redirect:/genders";
    }

    @PostMapping("/delete-gender")
    public String deleteGender(@RequestParam Integer genderId) {
        genderService.deleteGender(genderId);
        return "redirect:/genders";
    }

    @GetMapping("/update-gender")
    public String showUpdateForm(@RequestParam Integer genderId, Model model) {
        Gender gender = genderService.getGenderById(genderId);
        model.addAttribute("gender", gender);
        return "updategender";
    }

    @PostMapping("/update-gender")
    public String updateGender(@RequestParam Integer genderId, @RequestParam String gender, Model model) {
        String trimmedName = gender.trim();

        if (trimmedName.isEmpty() || gender.startsWith(" ") || gender.endsWith(" ")) {
            model.addAttribute("error", "El nombre no puede estar vacío o tener espacios al principio/final.");
            Gender gender1 = genderService.getGenderById(genderId);
            model.addAttribute("gender", gender1);
            return "updategender";
        }

        String message = genderService.updateGenderById(genderId, gender);
        if (message != null) {
            model.addAttribute("error", message);
            Gender gender1 = genderService.getGenderById(genderId);
            model.addAttribute("gender", gender1);
            return "updategender";
        }

        model.addAttribute("error", "El género ha sido modificado con éxito.");
        Gender gender1 = genderService.getGenderById(genderId);
        model.addAttribute("gender", gender1);
        return "updategender";
    }
}
