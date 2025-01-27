package com.esliceu.movie.controllers;

import com.esliceu.movie.models.*;
import com.esliceu.movie.services.AuthorizationService;
import com.esliceu.movie.services.KeywordService;
import com.esliceu.movie.services.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MovieController {

    @Autowired
    MovieService movieService;
    @Autowired
    KeywordService keywordService;
    @Autowired
    AuthorizationService authorizationService;

    @GetMapping("/movies")
    public String showMovies(Model model) {
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", 0);
        return "movies";
    }

    @PostMapping("/movies")
    public String searchMovies(@RequestParam String tags,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size,
                               Model model) {

        if (tags.equals("todas")) {
            Page<Movie> moviePage = movieService.getPaginatedMovies(page, size);
            model.addAttribute("movies", moviePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", moviePage.getTotalPages());
            model.addAttribute("totalItems", moviePage.getTotalElements());
            model.addAttribute("pageSize", size);
        } else if (tags.equals("genre")) {
            List<Movie> movies = movieService.findMoviesByGenre(keyword);

            String jsonToSend = movieService.getGenreJson();
            model.addAttribute("jsonInfo", jsonToSend);

            model.addAttribute("movies", movies);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", movies.size());
        }else if (tags.equals("director")) {
            List<Movie> movies = movieService.findMoviesByDirector(keyword);

            String jsonToSend = movieService.getDirectorsJson();
            model.addAttribute("jsonInfo", jsonToSend);

            model.addAttribute("movies", movies);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", movies.size());
        } else if (tags.equals("actor")) {
            List<Movie> movies = movieService.findMoviesByActor(keyword);
            
            String jsonToSend = movieService.getActorsJson();
            model.addAttribute("jsonInfo", jsonToSend);

            model.addAttribute("movies", movies);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", movies.size());
        } else if (tags.equals("character")) {
            List<Movie> movies = movieService.findMoviesByCharacter(keyword);

            String jsonToSend = movieService.getCharacterJson();
            model.addAttribute("jsonInfo", jsonToSend);

            model.addAttribute("movies", movies);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", movies.size());
        } else{
            Movie movie = movieService.searchMovies(tags, keyword);

            String jsonToSend = movieService.getMovieJson();
            model.addAttribute("jsonInfo", jsonToSend);

            model.addAttribute("movies", movie);
            model.addAttribute("currentPage", 0);
            model.addAttribute("totalPages", 1);
            model.addAttribute("pageSize", 1);
        }
        return "movies";
    }

    @GetMapping("/moviescrud")
    public String showMoviesCrud(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int size,
                                 Model model) {
        Page<Movie> moviePage = movieService.getPaginatedMovies(page, size);

        String jsonToSend = movieService.getMovieJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("movies", moviePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviePage.getTotalPages());
        model.addAttribute("totalItems", moviePage.getTotalElements());
        model.addAttribute("pageSize", size);
        return "moviescrud";
    }

    @PostMapping("/moviescrud")
    public  String showOneMovie(@RequestParam String tags,
                                Model model){

        Movie movie = movieService.searchMoviesByTitle(tags);

        model.addAttribute("movies", movie);
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
        model.addAttribute("pageSize", 1);
        return "moviescrud";
    }

    @PostMapping("/delete-movie")
    public String deleteMovie(@RequestParam Integer movieId, HttpSession session) {
        movieService.deleteMovie(movieId);
        return "redirect:/moviescrud";
    }

    @GetMapping("/moviecreate")
    public String showCreateMovieForm(Model model) {
        return "moviecreate";
    }

    @PostMapping("/moviecreate")
    public String createMovie(@ModelAttribute Movie movie, Model model) {
        movieService.save(movie);
        model.addAttribute("message", "La película ha sido creada con éxito.");
        return "moviecreate";
    }

    @GetMapping("/updatemovie")
    public String showUpdateMovieForm(@RequestParam("movieId") Integer movieId, Model model) {
        Movie movie = movieService.findById(movieId);
        model.addAttribute("movie", movie);
        return "updatemovie";
    }

    @PostMapping("/redirect")
    public String redirectTo(@RequestParam("tags") String tag,
                             @RequestParam("movieId") Integer movieId) {

        switch (tag) {
            case "keyword":
                return "redirect:/updatemoviekeyword?movieId=" + movieId;
            case "genre":
                return "redirect:/updatemoviegenre?movieId=" + movieId;
            case "language":
                return "redirect:/updatemovielanguage?movieId=" + movieId;
            case "country":
                return "redirect:/updatemoviecountry?movieId=" + movieId;
            case "company":
                return "redirect:/updatemoviecompany?movieId=" + movieId;
            case "gender":
                return "redirect:/updatemoviegender?movieId=" + movieId;
            case "personaje":
                return "redirect:/updatemoviepersonaje?movieId=" + movieId;
            default:
                return "redirect:/error";
        }
    }

    @GetMapping("/updatemoviekeyword")
    public String updateKeyword(@RequestParam(value = "movieId", required = false) Integer movieId, Model model) {
        Movie movie = movieService.findById(movieId);
        Set<MovieKeywords> movieKeywords = movie.getKeywords();
        model.addAttribute("movie", movie);
        model.addAttribute("moviekeywords", movieKeywords);
        return "updatemoviekeyword";
    }

    private static Set<String> getKeywords(Movie movie) {
        Set<MovieKeywords> movieKeywords = movie.getKeywords();
        Set<String> keywordNames = movieKeywords.stream()
                .map(movieKeyword -> movieKeyword.getKeyword().getKeywordName())
                .collect(Collectors.toSet());
        return keywordNames;
    }

    @PostMapping("/deletekeyword")
    public String deleteKeywordFromMovie(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("keywordId") Integer keywordId) {

        MovieKeywordsId movieKeywordsId = new MovieKeywordsId(movieId, keywordId);

        keywordService.deleteMovieKeyword(movieKeywordsId);
        return "redirect:/updatemoviekeyword?movieId=" + movieId;
    }
}
