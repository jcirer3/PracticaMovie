package com.esliceu.movie.services;

import com.esliceu.movie.DAO.MovieGenreDAO;
import com.esliceu.movie.DAO.MovieKeywordDAO;
import com.esliceu.movie.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieGenresService {
    @Autowired
    MovieGenreDAO movieGenreDAO;

    public void save(Movie movie, Genre genre) {
        MovieGenres movieGenres = new MovieGenres();
        MovieGenresId id = new MovieGenresId(movie.getMovieId(), genre.getGenreId());
        movieGenres.setId(id);
        movieGenres.setMovie(movie);
        movieGenres.setGenre(genre);
        movieGenreDAO.save(movieGenres);
    }
}
