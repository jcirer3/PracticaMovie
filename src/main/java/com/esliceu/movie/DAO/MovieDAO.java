package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieDAO extends JpaRepository<Movie, Integer> {
    Page<Movie> findAll(Pageable pageable);
    Movie findByTitle(String title);

    @Query(value = "SELECT DISTINCT m.* FROM movie m " +
            "JOIN movie_genres mg ON m.movie_id = mg.movie_id " +
            "JOIN genre g ON mg.genre_id = g.genre_id " +
            "WHERE g.genre_name = :genreName", nativeQuery = true)
    List<Movie> findByGenreNameNative(@Param("genreName") String genreName);

    @Query(value = "SELECT DISTINCT m.* FROM movie m " +
            "JOIN movie_cast mc ON m.movie_id = mc.movie_id " +
            "WHERE mc.character_name = :characterName", nativeQuery = true)
    List<Movie> findByCharacterNameNative(@Param("characterName") String characterName);


    //Movie findByActor(String keyword);
    //Movie findByDirector(String keyword);
}
