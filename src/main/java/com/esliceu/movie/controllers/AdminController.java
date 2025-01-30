package com.esliceu.movie.controllers;

import com.esliceu.movie.models.Authorization;
import com.esliceu.movie.models.AuthorizationState;
import com.esliceu.movie.models.User;
import com.esliceu.movie.services.AuthorizationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class AdminController {
    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/managePermissions")
    public String managePermissions(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "Debes iniciar sesión para acceder a esta página.");
            return "home";
        }
        if (!Objects.equals(user.getUserName(), "admin")) {
            model.addAttribute("error", "No tienes permiso.");
            return "home";
        }


        List<Authorization> pendingAuthorizations = authorizationService.getPendingAuthorizations();
        model.addAttribute("pendingAuthorizations", pendingAuthorizations);
        return "managePermissions";
    }


    @PostMapping("/updateAuthorization")
    public String updateAuthorization(
            @RequestParam Integer userId,
            @RequestParam Integer permissionId,
            @RequestParam String state) {
        AuthorizationState authorizationState = AuthorizationState.valueOf(state);
        authorizationService.updateAuthorizationState(userId, permissionId, authorizationState);
        return "redirect:/managePermissions";
    }
}
