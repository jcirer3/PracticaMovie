package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Genre;
import com.esliceu.movie.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreDAO extends JpaRepository<Genre, Integer> {
    Page<Genre> findAll(Pageable pageable);
    Genre findByGenreName(String genreName);
}
