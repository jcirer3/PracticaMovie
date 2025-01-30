package com.esliceu.movie.controllers;

import com.esliceu.movie.models.*;
import com.esliceu.movie.services.AuthorizationService;
import com.esliceu.movie.services.GenreService;
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
public class GenreController {
    @Autowired
    GenreService genreService;
    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/genres")
    public String listGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "Debes iniciar sesión para acceder a esta página.");
            return "home";
        }
        Permission permission = authorizationService.findPermissionByName("GenrePermission");
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

        Page<Genre> genrePage = genreService.getPaginatedGenres(page, size);

        String jsonToSend = genreService.getGenreJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("genres", genrePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", genrePage.getTotalPages());
        model.addAttribute("totalItems", genrePage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "genres";
    }

    @PostMapping("/genres")
    public String searchGenres(@RequestParam String genreName, Model model){
        List<Genre> genres = genreService.findByName(genreName);
        model.addAttribute("genres", genres);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", genres.size());
        return "genres";
    }

    @PostMapping("/add-genre")
    public String newGenre(@RequestParam String genreName, Model model) {
        String message = genreService.saveGenre(genreName);

        if (message != null){
            List<Genre> genres = genreService.findByName(genreName);
            model.addAttribute("genres", genres);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", genres.size());
            model.addAttribute("message", message);
            return "genres";
        }

        List<Genre> genres = genreService.findByName(genreName);
        model.addAttribute("genres", genres);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", genres.size());
        model.addAttribute("message", "El género de películas ha sido creado correctamente.");
        return "redirect:/genres";
    }

    @PostMapping("/delete-genre")
    public String deleteGenre(@RequestParam Integer genreId) {
        genreService.deleteGenre(genreId);
        return "redirect:/genres";
    }

    @GetMapping("/update-genre")
    public String showUpdateForm(@RequestParam Integer genreId, Model model) {
        Genre genre = genreService.getGenreById(genreId);
        model.addAttribute("genre", genre);
        return "updategenre";
    }

    @PostMapping("/update-genre")
    public String updateGenre(@RequestParam Integer genreId, @RequestParam String genreName, Model model) {
        String trimmedName = genreName.trim();
        if (trimmedName.isEmpty() || genreName.startsWith(" ") || genreName.endsWith(" ")) {
            model.addAttribute("error", "El nombre no puede estar vacío o tener espacios al principio/final.");
            Genre genre = genreService.getGenreById(genreId);
            model.addAttribute("genre", genre);
            return "updategenre";
        }
        String message = genreService.updateGenreNameById(genreId, genreName);
        if (message != null) {
            model.addAttribute("error", message);
            Genre genre = genreService.getGenreById(genreId);
            model.addAttribute("genre", genre);
            return "updategenre";
        }
        model.addAttribute("error", "El género de la película ha sido modificado con éxito.");
        Genre genre = genreService.getGenreById(genreId);
        model.addAttribute("genre", genre);
        return "updategenre";
    }
}
