package com.esliceu.movie.services;

import com.esliceu.movie.DAO.MovieCrewDAO;
import com.esliceu.movie.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieCrewService {
    @Autowired
    MovieCrewDAO movieCrewDAO;

    public void save(Movie movie, Person person, Department department, String job) {
        MovieCrew movieCrew = new MovieCrew();
        MovieCrewId id = new MovieCrewId(movie.getMovieId(), person.getPersonId(), department.getDepartmentId());
        movieCrew.setId(id);
        movieCrew.setMovie(movie);
        movieCrew.setPerson(person);
        movieCrew.setDepartment(department);
        movieCrew.setJob(job);
        movieCrewDAO.save(movieCrew);
    }
}
