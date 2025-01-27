package com.esliceu.movie.DAO;

import com.esliceu.movie.models.MovieGenres;
import com.esliceu.movie.models.MovieGenresId;
import com.esliceu.movie.models.MovieKeywords;
import com.esliceu.movie.models.MovieKeywordsId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieGenreDAO extends JpaRepository<MovieGenres, MovieGenresId> {

}
