package com.esliceu.movie.controllers;

import com.esliceu.movie.models.*;
import com.esliceu.movie.services.AuthorizationService;
import com.esliceu.movie.services.CountryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CountryController {
    @Autowired
    CountryService countryService;
    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/countrys")
    public String listCountrys(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "Debes iniciar sesión para acceder a esta página.");
            return "home";
        }

        Permission permission = authorizationService.findPermissionByName("CountryPermission");
        if (permission == null) {
            model.addAttribute("message", "No tienes permiso.");
            return "home";
        }

        Authorization authorization = authorizationService.findAuthorizationByIds(
                user.getUserId(), permission.getPermissionId());

        if (authorization == null || authorization.getState() != AuthorizationState.ACEPTED) {
            model.addAttribute("message", "No tienes permisos para acceder a esta página.");
            return "home";
        }

        Page<Country> countryPage = countryService.getPaginatedCountrys(page, size);

        String jsonToSend = countryService.getCountryJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("countrys", countryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", countryPage.getTotalPages());
        model.addAttribute("totalItems", countryPage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "countrys";
    }

    @PostMapping("/countrys")
    public String searchCountry(@RequestParam String countryName, Model model){
        List<Country> countrys = countryService.findByName(countryName);
        model.addAttribute("countrys", countrys);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", countrys.size());
        return "countrys";
    }

    @PostMapping("/add-country")
    public String newCountry(@RequestParam String countryName, Model model) {
        String message = countryService.saveCountry(countryName);

        if (message != null){
            List<Country> countrys = countryService.findByName(countryName);
            model.addAttribute("countrys", countrys);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", countrys.size());
            model.addAttribute("message", message);
            return "countrys";
        }

        List<Country> countrys = countryService.findByName(countryName);
        model.addAttribute("countrys", countrys);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", countrys.size());
        model.addAttribute("message", "El país ha sido creado correctamente.");
        return "redirect:/countrys";
    }

    @PostMapping("/delete-country")
    public String deleteCountry(@RequestParam Integer countryId) {
        countryService.deleteCountry(countryId);
        return "redirect:/countrys";
    }

    @GetMapping("/update-country")
    public String showUpdateForm(@RequestParam Integer countryId, Model model) {
        Country country = countryService.getCountryById(countryId);
        model.addAttribute("country", country);
        return "updatecountry";
    }

    @PostMapping("/update-country")
    public String updateCountry(@RequestParam Integer countryId, @RequestParam String countryName, Model model) {
        String trimmedName = countryName.trim();
        if (trimmedName.isEmpty() || countryName.startsWith(" ") || countryName.endsWith(" ")) {
            model.addAttribute("error", "El nombre no puede estar vacío o tener espacios al principio/final.");
            Country country = countryService.getCountryById(countryId);
            model.addAttribute("country", country);
            return "updatecountry";
        }
        String message = countryService.updateCountryNameById(countryId, countryName);
        if (message != null) {
            model.addAttribute("error", message);
            Country country = countryService.getCountryById(countryId);
            model.addAttribute("country", country);
            return "updatecountry";
        }
        model.addAttribute("error", "El país ha sido modificado con éxito.");
        Country country = countryService.getCountryById(countryId);
        model.addAttribute("country", country);
        return "updatecountry";
    }
}
