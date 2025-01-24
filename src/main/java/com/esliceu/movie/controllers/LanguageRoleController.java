package com.esliceu.movie.controllers;

import com.esliceu.movie.models.Language;
import com.esliceu.movie.models.LanguageRole;
import com.esliceu.movie.services.LanguageRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LanguageRoleController {
    @Autowired
    LanguageRoleService languageRoleService;

    @GetMapping("/languageroles")
    public String listLanguageRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        Page<LanguageRole> languageRolePage = languageRoleService.getPaginatedLanguagesRole(page, size);

        String jsonToSend = languageRoleService.getLanguageJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("languageroles", languageRolePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", languageRolePage.getTotalPages());
        model.addAttribute("totalItems", languageRolePage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "languageroles";
    }

    @PostMapping("/languageroles")
    public String searchLanguageRole(@RequestParam String languageRole, Model model){
        List<LanguageRole> languageRoles = languageRoleService.findByName(languageRole);
        model.addAttribute("languageroles", languageRoles);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", languageRoles.size());
        return "languageroles";
    }

    @PostMapping("/add-languagerole")
    public String newLanguageRole(@RequestParam String languageRole, Model model) {
        String message = languageRoleService.saveLanguageRole(languageRole);

        if (message != null){
            List<LanguageRole> languageRoles = languageRoleService.findByName(languageRole);
            model.addAttribute("languageroles", languageRoles);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", languageRoles.size());
            model.addAttribute("message", message);
            return "languageroles";
        }

        List<LanguageRole> languageRoles = languageRoleService.findByName(languageRole);
        model.addAttribute("languageroles", languageRoles);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", languageRoles.size());
        model.addAttribute("message", "El idioma role ha sido creado correctamente.");
        return "redirect:/languageroles";
    }

    @PostMapping("/delete-languagerole")
    public String deleteLanguageRole(@RequestParam Integer roleId) {
        languageRoleService.deleteLanguageRole(roleId);
        return "redirect:/languageroles";
    }

    @GetMapping("/update-languagerole")
    public String showUpdateForm(@RequestParam Integer roleId, Model model) {
        LanguageRole languageRole = languageRoleService.getLanguageById(roleId);
        model.addAttribute("languagerole", languageRole);
        return "updatelanguagerole";
    }

    @PostMapping("/update-languagerole")
    public String updateLanguageRole(@RequestParam Integer roleId, @RequestParam String languageRole, Model model) {
        String trimmedName = languageRole.trim();
        if (trimmedName.isEmpty() || languageRole.startsWith(" ") || languageRole.endsWith(" ")) {
            model.addAttribute("error", "El nombre no puede estar vacío o tener espacios al principio/final.");
            LanguageRole languageRole1 = languageRoleService.getLanguageById(roleId);
            model.addAttribute("languagerole", languageRole1);
            return "updatelanguagerole";
        }
        String message = languageRoleService.updateLanguageRoleById(roleId, languageRole);
        if (message != null) {
            model.addAttribute("error", message);
            LanguageRole languageRole1 = languageRoleService.getLanguageById(roleId);
            model.addAttribute("languagerole", languageRole1);
            return "updatelanguagerole";
        }
        model.addAttribute("error", "El idioma role ha sido modificado con éxito.");
        LanguageRole languageRole1 = languageRoleService.getLanguageById(roleId);
        model.addAttribute("languagerole", languageRole1);
        return "updatelanguagerole";
    }
}
