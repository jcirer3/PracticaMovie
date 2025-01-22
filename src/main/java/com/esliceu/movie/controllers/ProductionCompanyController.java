package com.esliceu.movie.controllers;

import com.esliceu.movie.models.ProductionCompany;
import com.esliceu.movie.services.ProductionCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductionCompanyController {
    @Autowired
    ProductionCompanyService productionCompanyService;

    @GetMapping("/companys")
    public String listCompanys(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model) {
        Page<ProductionCompany> companyPage = productionCompanyService.getPaginatedCompanys(page, size);

        String jsonToSend = productionCompanyService.getCompanyJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("companys", companyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", companyPage.getTotalPages());
        model.addAttribute("totalItems", companyPage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "companys";
    }

    @PostMapping("/companys")
    public String searchCompany(@RequestParam String companyName, Model model){
        List<ProductionCompany> companys = productionCompanyService.findByName(companyName);
        model.addAttribute("companys", companys);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", companys.size());
        return "companys";
    }

    @PostMapping("/add-company")
    public String newCompany(@RequestParam String companyName, Model model) {
        String message = productionCompanyService.saveCompany(companyName);

        if (message != null){
            List<ProductionCompany> companys = productionCompanyService.findByName(companyName);
            model.addAttribute("companys", companys);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", companys.size());
            model.addAttribute("message", message);
            return "companys";
        }

        List<ProductionCompany> companys = productionCompanyService.findByName(companyName);
        model.addAttribute("companys", companys);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", companys.size());
        model.addAttribute("message", "La productora ha sido creado correctamente.");
        return "redirect:/companys";
    }

    @PostMapping("/delete-company")
    public String deleteCompany(@RequestParam Integer companyId) {
        productionCompanyService.deleteCompany(companyId);
        return "redirect:/companys";
    }

    @GetMapping("/update-company")
    public String showUpdateForm(@RequestParam Integer companyId, Model model) {
        ProductionCompany productionCompany = productionCompanyService.getCompanyById(companyId);
        model.addAttribute("company", productionCompany);
        return "updatecompany";
    }

    @PostMapping("/update-company")
    public String updateCompany(@RequestParam Integer companyId, @RequestParam String companyName, Model model) {
        String trimmedName = companyName.trim();
        if (trimmedName.isEmpty() || companyName.startsWith(" ") || companyName.endsWith(" ")) {
            model.addAttribute("error", "El nombre no puede estar vacío o tener espacios al principio/final.");
            ProductionCompany productionCompany = productionCompanyService.getCompanyById(companyId);
            model.addAttribute("company", productionCompany);
            return "updatecompany";
        }
        String message = productionCompanyService.updateCompanyNameById(companyId, companyName);
        if (message != null) {
            model.addAttribute("error", message);
            ProductionCompany productionCompany = productionCompanyService.getCompanyById(companyId);
            model.addAttribute("company", productionCompany);
            return "updatecompany";
        }
        model.addAttribute("error", "La productora ha sido modificado con éxito.");
        ProductionCompany productionCompany = productionCompanyService.getCompanyById(companyId);
        model.addAttribute("company", productionCompany);
        return "updatecompany";
    }
}
