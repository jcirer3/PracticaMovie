package com.esliceu.movie.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countryId;

    @Column(length = 10)
    private String countryIsoCode;

    @Column(length = 200)
    private String countryName;

    @OneToMany(mappedBy = "country")
    private Set<ProductionCountry> movies;

    public Set<ProductionCountry> getMovies() {
        return movies;
    }

    public void setMovies(Set<ProductionCountry> movies) {
        this.movies = movies;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public void setCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
