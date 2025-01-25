package com.esliceu.movie.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieCrewId implements Serializable {
    @Column(name = "movie_id")
    private Integer movieId;
    @Column(name = "department_id")
    private Integer departmentId;
    @Column(name = "person_id")
    private Integer personId;

    public MovieCrewId() {
    }

    public MovieCrewId(Integer movieId, Integer departmentId, Integer personId) {
        this.movieId = movieId;
        this.departmentId = departmentId;
        this.personId = personId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
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
        MovieCrewId that = (MovieCrewId) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(departmentId, that.departmentId) && Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, departmentId, personId);
    }
}
