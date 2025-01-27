package com.esliceu.movie.DAO;

import com.esliceu.movie.models.MovieKeywords;
import com.esliceu.movie.models.MovieKeywordsId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieKeywordDAO extends JpaRepository<MovieKeywords, MovieKeywordsId> {

}
