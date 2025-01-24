package com.esliceu.movie.controllers;

import com.esliceu.movie.models.Language;
import com.esliceu.movie.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LanguageController {
    @Autowired
    LanguageService languageService;

    @GetMapping("/languages")
    public String listLanguages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        Page<Language> languagePage = languageService.getPaginatedLanguages(page, size);

        String jsonToSend = languageService.getLanguageJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("languages", languagePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", languagePage.getTotalPages());
        model.addAttribute("totalItems", languagePage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "languages";
    }

    @PostMapping("/languages")
    public String searchLanguage(@RequestParam String languageName, Model model){
        List<Language> languages = languageService.findByName(languageName);
        model.addAttribute("languages", languages);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", languages.size());
        return "languages";
    }

    @PostMapping("/add-language")
    public String newLanguage(@RequestParam String languageName, Model model) {
        String message = languageService.saveLanguage(languageName);

        if (message != null){
            List<Language> languages = languageService.findByName(languageName);
            model.addAttribute("languages", languages);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", languages.size());
            model.addAttribute("message", message);
            return "languages";
        }

        List<Language> languages = languageService.findByName(languageName);
        model.addAttribute("languages", languages);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", languages.size());
        model.addAttribute("message", "El idioma ha sido creado correctamente.");
        return "redirect:/languages";
    }

    @PostMapping("/delete-language")
    public String deleteLanguage(@RequestParam Integer languageId) {
        languageService.deleteLanguage(languageId);
        return "redirect:/languages";
    }

    @GetMapping("/update-language")
    public String showUpdateForm(@RequestParam Integer languageId, Model model) {
        Language language = languageService.getLanguageById(languageId);
        model.addAttribute("language", language);
        return "updatelanguage";
    }

    @PostMapping("/update-language")
    public String updateLanguage(@RequestParam Integer languageId, @RequestParam String languageName, Model model) {
        String trimmedName = languageName.trim();
        if (trimmedName.isEmpty() || languageName.startsWith(" ") || languageName.endsWith(" ")) {
            model.addAttribute("error", "El nombre no puede estar vacío o tener espacios al principio/final.");
            Language language = languageService.getLanguageById(languageId);
            model.addAttribute("language", language);
            return "updatelanguage";
        }
        String message = languageService.updateLanguageNameById(languageId, languageName);
        if (message != null) {
            model.addAttribute("error", message);
            Language language = languageService.getLanguageById(languageId);
            model.addAttribute("language", language);
            return "updatelanguage";
        }
        model.addAttribute("error", "El idioma ha sido modificado con éxito.");
        Language language = languageService.getLanguageById(languageId);
        model.addAttribute("language", language);
        return "updatelanguage";
    }
}
