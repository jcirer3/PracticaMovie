package com.esliceu.movie.controllers;

import com.esliceu.movie.models.Movie;
import com.esliceu.movie.models.MovieKeywords;
import com.esliceu.movie.services.KeywordService;
import com.esliceu.movie.services.MovieService;
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
public class MovieController {

    @Autowired
    MovieService movieService;
    @Autowired
    KeywordService keywordService;

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
    public String deleteMovie(@RequestParam Integer movieId) {
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
        Set<String> keywordNames = getKeywords(movie);


        model.addAttribute("movie", movie);
        model.addAttribute("keywords", keywordNames);
        return "moviecreate";
    }

    private static Set<String> getKeywords(Movie movie) {
        Set<MovieKeywords> movieKeywords = movie.getKeywords();
        Set<String> keywordNames = movieKeywords.stream()
                .map(movieKeyword -> movieKeyword.getKeyword().getKeywordName())
                .collect(Collectors.toSet());
        return keywordNames;
    }

    @PostMapping("/deleteKeyword")
    public String deleteKeyword(@RequestParam("keywordName") String keywordName,
                                @RequestParam("movieId") Integer movieId, Model model) {
        System.out.println("movieId: " + movieId);  // Para verificar en los logs que movieId está llegando
        System.out.println("keywordName: " + keywordName);

        Movie movie = movieService.findById(movieId);
        Integer keywordId = keywordService.getKeywordIdByName(keywordName);
        movieService.deleteKeyword(movieId, keywordId);


        Set<String> keywordNames = getKeywords(movie);

        model.addAttribute("movie", movie);
        model.addAttribute("keywords", keywordNames);

        return "updatemovie";
    }
}
