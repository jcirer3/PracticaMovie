package com.esliceu.movie.DAO;

import com.esliceu.movie.models.MovieKeywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MovieKeywordDAO extends JpaRepository<MovieKeywords, Integer> {
    @Query("DELETE FROM MovieKeywords mk WHERE mk.movie.id = :movieId AND mk.keyword.id = :keywordId")
    void deleteByMovieIdAndKeywordId(@Param("movieId") Integer movieId, @Param("keywordId") Integer keywordId);
}
