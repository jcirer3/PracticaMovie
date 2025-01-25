package com.esliceu.movie.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieLanguagesId implements Serializable {
    @Column(name = "movie_id")
    private Integer movieId;
    @Column(name = "language_id")
    private Integer languageId;
    @Column(name = "language_role_id")
    private Integer languageRoleId;

    public MovieLanguagesId() {
    }

    public MovieLanguagesId(Integer movieId, Integer languageId, Integer languageRoleId) {
        this.movieId = movieId;
        this.languageId = languageId;
        this.languageRoleId = languageRoleId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public Integer getLanguageRoleId() {
        return languageRoleId;
    }

    public void setLanguageRoleId(Integer languageRoleId) {
        this.languageRoleId = languageRoleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieLanguagesId that = (MovieLanguagesId) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(languageId, that.languageId) && Objects.equals(languageRoleId, that.languageRoleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, languageId, languageRoleId);
    }
}
