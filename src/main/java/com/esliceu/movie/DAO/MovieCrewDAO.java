package com.esliceu.movie.DAO;

import com.esliceu.movie.models.MovieCrew;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MovieCrewDAO extends JpaRepository<MovieCrew, Integer> {
    Page<MovieCrew> findAll(Pageable pageable);

    List<MovieCrew> findByJob(String director);

    @Query("SELECT mc FROM MovieCrew mc JOIN FETCH mc.person WHERE mc.job = 'director'")
    List<MovieCrew> findDirectorsWithPerson();

}
