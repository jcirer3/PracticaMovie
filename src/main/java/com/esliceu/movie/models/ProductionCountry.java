package com.esliceu.movie.models;

import jakarta.persistence.*;

@Entity
public class ProductionCountry {

    @EmbeddedId
    private ProductionCountryId id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("countryId") // Mapea la propiedad companyId de MovieCompanyId con la columna companyId
    @JoinColumn(name = "country_id")
    private Country country;

    public ProductionCountryId getId() {
        return id;
    }

    public void setId(ProductionCountryId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
