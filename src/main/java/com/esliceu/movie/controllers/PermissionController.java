package com.esliceu.movie.controllers;

import com.esliceu.movie.models.*;
import com.esliceu.movie.services.AuthorizationService;
import com.esliceu.movie.services.CountryService;
import com.esliceu.movie.services.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class PermissionController {
    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/permission")
    public String showCreateMovieForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println(user.getUserId());
        Set<Authorization> authorizations = authorizationService.getPermissionsByUserId(user.getUserId());

        Set<Permission> permissions = authorizations.stream()
                .map(Authorization::getPermission) //
                .collect(Collectors.toSet());

        model.addAttribute("permissions", permissions);
        return "permission";
    }

}
