package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDAO extends JpaRepository<Movie, Integer> {
    Page<Movie> findAll(Pageable pageable);
    Movie findByTitle(String title);
    //Movie findByActor(String keyword);
    //Movie findByCharacter(String keyword);
    //Movie findByGenre(String keyword);
    //Movie findByDirector(String keyword);
}
