package com.esliceu.movie.services;

import com.esliceu.movie.DAO.*;
import com.esliceu.movie.models.Genre;
import com.esliceu.movie.models.Movie;
import com.esliceu.movie.models.MovieCast;
import com.esliceu.movie.models.MovieCrew;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    MovieDAO movieDAO;
    @Autowired
    GenreDAO genreDAO;
    @Autowired
    MovieCastDAO movieCastDAO;
    @Autowired
    MovieCrewDAO movieCrewDAO;
    @Autowired
    MovieKeywordDAO movieKeywordDAO;


    public Page<Movie> getPaginatedMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return movieDAO.findAll(pageable);
    }

    public String getMovieJson() {
        List<Movie> movies = movieDAO.findAll();
        List<String> names = movies.stream()
                .map(m -> m.getTitle())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public List<Movie> findAll() {
        return movieDAO.findAll();
    }

    public Movie searchMovies(String searchBy, String keyword) {
        switch (searchBy) {
            case "actor":
                //return movieDAO.findByActor(keyword);
            case "character":
                //return movieDAO.findByCharacter(keyword);
            case "title":
                return movieDAO.findByTitle(keyword);
            case "genre":
                //return movieDAO.findByGenre(keyword);
            case "director":
                //return movieDAO.findByDirector(keyword);
        }
        return null;
    }

    public List<Movie> findMoviesByGenre(String keyword) {
        return movieDAO.findByGenreNameNative(keyword);
    }
    public List<Movie> findMoviesByCharacter(String keyword){
        return movieDAO.findByCharacterNameNative(keyword);
    }

    public String getGenreJson() {
        List<Genre> genres = genreDAO.findAll();
        List<String> names = genres.stream()
                .map(m -> m.getGenreName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public String getCharacterJson(){
        List<MovieCast> characters = movieCastDAO.findAll();
        List<String> names = characters.stream()
                .map(n -> n.getCharacterName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public String getDirectorsJson() {
        List<MovieCrew> movieCrews = movieCrewDAO.findDirectorsWithPerson();

        List<String> directors = movieCrews.stream()
                .map(mc -> mc.getPerson().getPersonName())
                .distinct()
                .collect(Collectors.toList());

        Gson gson = new Gson();
        return gson.toJson(directors);
    }

    public String getActorsJson() {
        // Usar la consulta optimizada para obtener solo los nombres de los actores
        List<String> actors = movieCastDAO.findDistinctActorNames();

        // Convertir la lista de nombres de actores a JSON
        Gson gson = new Gson();
        return gson.toJson(actors);
    }

    public List<Movie> findMoviesByDirector(String keyword) {
        return movieDAO.findMoviesByDirector("director", keyword);
    }

    public List<Movie> findMoviesByActor(String actorName) {
        return movieCastDAO.findMoviesByActorName(actorName);
    }

    public Movie searchMoviesByTitle(String keyword) {
        return movieDAO.findByTitle(keyword);
    }

    public void deleteMovie(Integer movieId) {
        movieDAO.deleteById(movieId);
    }

    public void save(Movie movie) {
        movieDAO.save(movie);
    }

    public Movie findById(Integer movieId) {
        return movieDAO.findById(movieId).get();
    }
}
