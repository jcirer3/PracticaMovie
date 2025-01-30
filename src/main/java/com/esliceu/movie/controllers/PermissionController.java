package com.esliceu.movie.controllers;

import com.esliceu.movie.models.*;
import com.esliceu.movie.services.AuthorizationService;
import com.esliceu.movie.services.PermissionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class PermissionController {
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    PermissionService permissionService;

    @GetMapping("/permission")
    public String showPermissions(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("login", "Debes iniciar sesión para acceder a esta página.");
            return "home";
        }

        Set<Authorization> authorizations = authorizationService.getPermissionsByUserId(user.getUserId());
        Set<Permission> userPermissions = authorizations.stream()
                .map(Authorization::getPermission)
                .collect(Collectors.toSet());

        List<Permission> allPermissions = permissionService.getAllPermissions();
        List<Permission> availablePermissions = allPermissions.stream()
                .filter(permission -> !userPermissions.contains(permission))
                .collect(Collectors.toList());

        model.addAttribute("authorizations", authorizations);
        model.addAttribute("allPermissions", availablePermissions);
        return "permission";
    }

    @PostMapping("/requestPermission")
    public String requestPermission(@RequestParam Integer permissionId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        authorizationService.requestPermission(user , permissionId);
        return "redirect:/permission";
    }
}
