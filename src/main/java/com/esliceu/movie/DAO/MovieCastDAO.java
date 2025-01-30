package com.esliceu.movie.DAO;


import com.esliceu.movie.models.Movie;
import com.esliceu.movie.models.MovieCast;
import com.esliceu.movie.models.MovieCastId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MovieCastDAO extends JpaRepository<MovieCast, MovieCastId> {
    Page<MovieCast> findAll(Pageable pageable);
    @Query("SELECT mc.movie FROM MovieCast mc WHERE mc.person.personName = :actorName")
    List<Movie> findMoviesByActorName(String actorName);

    @Query("SELECT DISTINCT mc.person.personName FROM MovieCast mc")
    List<String> findDistinctActorNames();

    MovieCast findByMovie_MovieIdAndPerson_PersonIdAndGender_GenderId(Integer movieId, Integer personId, Integer genderId);
}
