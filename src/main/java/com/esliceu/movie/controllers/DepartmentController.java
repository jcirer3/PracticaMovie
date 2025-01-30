package com.esliceu.movie.controllers;

import com.esliceu.movie.models.*;
import com.esliceu.movie.services.AuthorizationService;
import com.esliceu.movie.services.DepartmentService;
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
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/departments")
    public String listDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "Debes iniciar sesión para acceder a esta página.");
            return "home";
        }

        Permission permission = authorizationService.findPermissionByName("DepartmentPermission");
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

        Page<Department> departmentPage = departmentService.getPaginatedCDepartments(page, size);

        String jsonToSend = departmentService.getDepartmentJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("departments", departmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", departmentPage.getTotalPages());
        model.addAttribute("totalItems", departmentPage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "departments";
    }

    @PostMapping("/departments")
    public String searchDepartment(@RequestParam String departmentName, Model model){
        List<Department> departments = departmentService.findByName(departmentName);
        model.addAttribute("departments", departments);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", departments.size());
        return "departments";
    }

    @PostMapping("/add-department")
    public String newDepartment(@RequestParam String departmentName, Model model) {
        String message = departmentService.saveDepartment(departmentName);

        if (message != null){
            List<Department> departments = departmentService.findByName(departmentName);
            model.addAttribute("departments", departments);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", departments.size());
            model.addAttribute("message", message);
            return "departments";
        }

        List<Department> departments = departmentService.findByName(departmentName);
        model.addAttribute("departments", departments);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", departments.size());
        model.addAttribute("message", "El departamento ha sido creado correctamente.");
        return "redirect:/departments";
    }

    @PostMapping("/delete-department")
    public String deleteDepartment(@RequestParam Integer departmentId) {
        departmentService.deleteDepartment(departmentId);
        return "redirect:/departments";
    }

    @GetMapping("/update-department")
    public String showUpdateForm(@RequestParam Integer departmentId, Model model) {
        Department department = departmentService.getDepartmentById(departmentId);
        model.addAttribute("department", department);
        return "updatedepartment";
    }

    @PostMapping("/update-department")
    public String updateDepartment(@RequestParam Integer departmentId, @RequestParam String departmentName, Model model) {
        String trimmedName = departmentName.trim();
        if (trimmedName.isEmpty() || departmentName.startsWith(" ") || departmentName.endsWith(" ")) {
            model.addAttribute("error", "El nombre no puede estar vacío o tener espacios al principio/final.");
            Department department = departmentService.getDepartmentById(departmentId);
            model.addAttribute("department", department);
            return "updatedepartment";
        }
        String message = departmentService.updateDepartmentNameById(departmentId, departmentName);
        if (message != null) {
            model.addAttribute("error", message);
            Department department = departmentService.getDepartmentById(departmentId);
            model.addAttribute("department", department);
            return "updatedepartment";
        }
        model.addAttribute("error", "El departamento ha sido modificado con éxito.");
        Department department = departmentService.getDepartmentById(departmentId);
        model.addAttribute("department", department);
        return "updatedepartment";
    }
}
