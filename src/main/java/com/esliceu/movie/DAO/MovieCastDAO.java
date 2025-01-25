package com.esliceu.movie.DAO;


import com.esliceu.movie.models.MovieCast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieCastDAO extends JpaRepository<MovieCast, Integer> {
    Page<MovieCast> findAll(Pageable pageable);
}
