package com.esliceu.movie.DAO;

import com.esliceu.movie.models.MovieGenres;
import com.esliceu.movie.models.MovieGenresId;
import com.esliceu.movie.models.MovieLanguages;
import com.esliceu.movie.models.MovieLanguagesId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieLanguageDAO extends JpaRepository<MovieLanguages, MovieLanguagesId> {

}
