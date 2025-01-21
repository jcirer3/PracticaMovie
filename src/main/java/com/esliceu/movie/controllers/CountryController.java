package com.esliceu.movie.controllers;

import com.esliceu.movie.models.Country;
import com.esliceu.movie.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CountryController {
    @Autowired
    CountryService countryService;

    @GetMapping("/countrys")
    public String listPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        Page<Country> countryPage = countryService.getPaginatedPersons(page, size);



        model.addAttribute("countrys", countryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", countryPage.getTotalPages());
        model.addAttribute("totalItems", countryPage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "countrys";
    }
}
