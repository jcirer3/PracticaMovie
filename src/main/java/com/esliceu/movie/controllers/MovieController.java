package com.esliceu.movie.controllers;

import com.esliceu.movie.models.Movie;
import com.esliceu.movie.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    MovieService movieService;

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
        }  else if (tags.equals("character")) {
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
}
