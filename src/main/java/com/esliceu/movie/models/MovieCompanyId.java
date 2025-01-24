package com.esliceu.movie.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieCompanyId implements Serializable {
    @Column(name = "movie_id")
    private Integer movieId;
    @Column(name = "company_id")
    private Integer companyId;

    public MovieCompanyId() {
    }

    public MovieCompanyId(Integer movieId, Integer companyId) {
        this.movieId = movieId;
        this.companyId = companyId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCompanyId that = (MovieCompanyId) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, companyId);
    }
}
