package com.esliceu.movie.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieCastId implements Serializable {
    @Column(name = "movie_id")
    private Integer movieId;
    @Column(name = "gender_id")
    private Integer genderId;
    @Column(name = "person_id")
    private Integer personId;

    public MovieCastId() {
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCastId that = (MovieCastId) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(genderId, that.genderId) && Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, genderId, personId);
    }
}
