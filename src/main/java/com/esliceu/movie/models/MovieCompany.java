package com.esliceu.movie.models;

import jakarta.persistence.*;

@Entity
public class MovieCompany {

    @EmbeddedId
    private MovieCompanyId id;

    @ManyToOne
    @MapsId("movieId") // Mapea la propiedad movieId de MovieCompanyId con la columna movieId
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("companyId") // Mapea la propiedad companyId de MovieCompanyId con la columna companyId
    @JoinColumn(name = "company_id")
    private ProductionCompany productionCompany;

    public MovieCompanyId getId() {
        return id;
    }

    public void setId(MovieCompanyId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public ProductionCompany getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(ProductionCompany productionCompany) {
        this.productionCompany = productionCompany;
    }
}
