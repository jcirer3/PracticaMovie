package com.esliceu.movie.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductionCountryId implements Serializable {
    @Column(name = "movie_id")
    private Integer movieId;
    @Column(name = "country_id")
    private Integer countryId;

    public ProductionCountryId() {
    }

    public ProductionCountryId(Integer movieId, Integer countryId) {
        this.movieId = movieId;
        this.countryId = countryId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionCountryId that = (ProductionCountryId) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(countryId, that.countryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, countryId);
    }
}
