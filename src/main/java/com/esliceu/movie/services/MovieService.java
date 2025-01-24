package com.esliceu.movie.services;

import com.esliceu.movie.DAO.MovieDAO;
import com.esliceu.movie.models.Movie;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    MovieDAO movieDAO;

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
}
