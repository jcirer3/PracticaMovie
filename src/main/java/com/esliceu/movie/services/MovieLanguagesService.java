package com.esliceu.movie.services;

import com.esliceu.movie.DAO.MovieGenreDAO;
import com.esliceu.movie.DAO.MovieLanguageDAO;
import com.esliceu.movie.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieLanguagesService {
    @Autowired
    MovieLanguageDAO movieLanguageDAO;

    public void save(Movie movie, Language language, LanguageRole languageRole) {
        MovieLanguages movieLanguages = new MovieLanguages();
        MovieLanguagesId id = new MovieLanguagesId(movie.getMovieId(), language.getLanguageId(),  1);
        movieLanguages.setId(id);
        movieLanguages.setLanguage(language);
        movieLanguages.setMovie(movie);
        movieLanguages.setLanguageRole(languageRole);
        movieLanguageDAO.save(movieLanguages);
    }
}
