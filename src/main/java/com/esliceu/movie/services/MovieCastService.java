package com.esliceu.movie.services;

import com.esliceu.movie.DAO.MovieCastDAO;
import com.esliceu.movie.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieCastService {
    @Autowired
    MovieCastDAO movieCastDAO;

    public void save(Movie movie, Person person, Gender gender, String characterName) {
        MovieCast movieCast = new MovieCast();
        MovieCastId id = new MovieCastId(movie.getMovieId(), person.getPersonId(), gender.getGenderId());
        movieCast.setId(id);
        movieCast.setMovie(movie);
        movieCast.setPerson(person);
        movieCast.setGender(gender);
        movieCast.setCharacterName(characterName);
        movieCastDAO.save(movieCast);
    }
}
