package com.esliceu.movie.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieGenresId implements Serializable {
    @Column(name = "movie_id")
    private Integer movieId;
    @Column(name = "genre_id")
    private Integer genreId;

    public MovieGenresId() {
    }

    public MovieGenresId(Integer movieId, Integer genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieGenresId that = (MovieGenresId) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, genreId);
    }
}
