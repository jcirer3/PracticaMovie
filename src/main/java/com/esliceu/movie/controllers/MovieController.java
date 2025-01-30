package com.esliceu.movie.controllers;

import com.esliceu.movie.models.*;
import com.esliceu.movie.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class MovieController {

    @Autowired
    MovieService movieService;
    @Autowired
    KeywordService keywordService;
    @Autowired
    MovieKeywordsService movieKeywordsService;
    @Autowired
    GenreService genreService;
    @Autowired
    MovieGenresService movieGenresService;
    @Autowired
    LanguageService languageService;
    @Autowired
    MovieLanguagesService movieLanguagesService;
    @Autowired
    PersonService personService;
    @Autowired
    MovieCrewService movieCrewService;
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    GenderService genderService;
    @Autowired
    MovieCastService movieCastService;

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
                                 Model model,
                                 HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("message", "Debes iniciar sesión para acceder a esta página.");
            return "home";
        }
        Permission permission = authorizationService.findPermissionByName("MoviePermission");
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
            case "crew":
                return "redirect:/updatemoviecrew?movieId=" + movieId;
            case "cast":
                return "redirect:/updatemoviecast?movieId=" + movieId;
            default:
                return "redirect:/error";
        }
    }

    @GetMapping("/updatemoviekeyword")
    public String updateKeyword(@RequestParam(value = "movieId", required = false) Integer movieId, Model model) {
        Movie movie = movieService.findById(movieId);
        Set<MovieKeywords> movieKeywords = movie.getKeywords();

        String jsonToSend = keywordService.getKeywordJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("movie", movie);
        model.addAttribute("moviekeywords", movieKeywords);
        return "updatemoviekeyword";
    }

    @PostMapping("/deletekeyword")
    public String deleteKeywordFromMovie(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("keywordId") Integer keywordId) {

        MovieKeywordsId movieKeywordsId = new MovieKeywordsId(movieId, keywordId);

        keywordService.deleteMovieKeyword(movieKeywordsId);
        return "redirect:/updatemoviekeyword?movieId=" + movieId;
    }
    @PostMapping("/addmoviekeyword")
    public String addMovieKeyword(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("keywordName") String keywordName) {

        Movie movie = movieService.findById(movieId);
        Keyword keyword = keywordService.findByKeywordName(keywordName);
        movieKeywordsService.save(movie, keyword);

        return "redirect:/updatemoviekeyword?movieId=" + movieId;
    }

    @GetMapping("/updatemoviegenre")
    public String updateGenre(@RequestParam(value = "movieId", required = false) Integer movieId, Model model) {
        Movie movie = movieService.findById(movieId);
        Set<MovieGenres> moviegenres = movie.getGenres();

        String jsonToSend = movieService.getGenreJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("movie", movie);
        model.addAttribute("moviegenres", moviegenres);
        return "updatemoviegenre";
    }

    @PostMapping("/deletegenre")
    public String deleteGenreFromMovie(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("genreId") Integer genreId) {

        MovieGenresId movieGenresId = new MovieGenresId(movieId, genreId);

        genreService.deleteMovieGenre(movieGenresId);
        return "redirect:/updatemoviegenre?movieId=" + movieId;
    }

    @PostMapping("/addmoviegenre")
    public String addMovieGenre(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("genreName") String genreName) {

        Movie movie = movieService.findById(movieId);
        Genre genre = genreService.findByGenreName(genreName);
        movieGenresService.save(movie, genre);

        return "redirect:/updatemoviegenre?movieId=" + movieId;
    }

    @GetMapping("/updatemovielanguage")
    public String updateLanguage(@RequestParam(value = "movieId", required = false) Integer movieId, Model model) {
        Movie movie = movieService.findById(movieId);
        Set<MovieLanguages> movieLanguages = movie.getLanguages();

        String jsonToSend = movieService.getLanguageJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("movie", movie);
        model.addAttribute("movielanguages", movieLanguages);
        return "updatemovielanguage";
    }

    @PostMapping("/deletelanguage")
    public String deleteLanguageFromMovie(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("languageId") Integer languageId) {


        MovieLanguagesId movieLanguagesId = new MovieLanguagesId(movieId, languageId, 2);
        languageService.deleteMovieLanguage(movieLanguagesId);

        return "redirect:/updatemovielanguage?movieId=" + movieId;
    }

    @PostMapping("/addmovielanguage")
    public String addMovieLanguage(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("languageName") String languageName) {

        Movie movie = movieService.findById(movieId);
        Language language = languageService.findByLanguageName(languageName);
        LanguageRole languageRole = new LanguageRole();

        movieLanguagesService.save(movie, language, languageRole);

        return "redirect:/updatemovielanguage?movieId=" + movieId;
    }

    @GetMapping("/updatemoviecrew")
    public String updateCrew(@RequestParam(value = "movieId", required = false) Integer movieId, Model model) {
        Movie movie = movieService.findById(movieId);
        Set<MovieCrew> movieCrews = movie.getJob();

        String jsonToSend = movieService.getAllJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("movie", movie);
        model.addAttribute("moviecrews", movieCrews);
        return "updatemoviecrew";
    }

    @PostMapping("/deletecrew")
    public String deleteCrewFromMovie(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("personId") Integer personId,
            @RequestParam("department") Integer departmentId) {

        MovieCrewId movieCrewId = new MovieCrewId(movieId, personId, departmentId);
        personService.deleteMovieCrew(movieCrewId);

        return "redirect:/updatemoviecrew?movieId=" + movieId;
    }

    @PostMapping("/addmoviecrew")
    public String addMovieCrew(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("personName") String personName,
            @RequestParam("job") String job) {

        Movie movie = movieService.findById(movieId);
        Person person = personService.findByPersonName(personName);
        Department department = new Department();
        movieCrewService.save(movie, person, department, job);
        return "redirect:/updatemoviecrew?movieId=" + movieId;
    }


    @GetMapping("/updatemoviecast")
    public String updateCast(@RequestParam(value = "movieId", required = false) Integer movieId, Model model) {
        List<Gender> genders = genderService.findAll();
        model.addAttribute("genders", genders);

        Movie movie = movieService.findById(movieId);
        Set<MovieCast> movieCasts = movie.getCharacterName();

        String jsonToSend = movieService.getAllJson();
        model.addAttribute("jsonInfo", jsonToSend);

        model.addAttribute("movie", movie);
        model.addAttribute("moviecasts", movieCasts);
        return "updatemoviecast";
    }

    @PostMapping("/deletecast")
    public String deleteCastFromMovie(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("personId") Integer personId,
            @RequestParam("genderId") Integer genderId) {


        personService.deleteMovieCast(movieId, personId, genderId);

        return "redirect:/updatemoviecast?movieId=" + movieId;
    }

    @PostMapping("/addmoviecast")
    public String addMovieCast(
            @RequestParam("movieId") Integer movieId,
            @RequestParam("personName") String personName,
            @RequestParam("genderId") Integer genderId,
            @RequestParam("characterName") String characterName) {

        Movie movie = movieService.findById(movieId);
        Person person = personService.findByPersonName(personName);
        Gender gender = genderService.findById(genderId);
        movieCastService.save(movie, person, gender, characterName);
        return "redirect:/updatemoviecast?movieId=" + movieId;
    }

    @GetMapping("/moviedates/{id}")
    public String getMovieDetails(@PathVariable Integer id, Model model) {
        Movie movie = movieService.findById(id);
        Set<MovieKeywords> movieKeywords = movie.getKeywords();
        Set<MovieGenres> moviegenres = movie.getGenres();
        Set<MovieLanguages> movieLanguages = movie.getLanguages();
        Set<ProductionCountry> productionCompanies = movie.getCountries();
        Set<MovieCompany> companies = movie.getCompanies();
        Set<MovieCast> movieCast = movie.getCharacterName();
        Set<MovieCrew> movieCrew = movie.getJob();

        model.addAttribute("moviecasts", movieCast);
        model.addAttribute("moviecrews", movieCrew);
        model.addAttribute("companys", companies);
        model.addAttribute("productrioncompanys", productionCompanies);
        model.addAttribute("movielanguages", movieLanguages);
        model.addAttribute("moviegenres", moviegenres);
        model.addAttribute("moviekeywords", movieKeywords);
        model.addAttribute("movie", movie);
        return "moviedates";
    }
}
