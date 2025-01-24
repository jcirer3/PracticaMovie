package com.esliceu.movie.models;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class ProductionCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    @Column(length = 500)
    private String companyName;

    @OneToMany(mappedBy = "productionCompany")
    private Set<MovieCompany> movies; // Relación con MovieCompany

    public Set<MovieCompany> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieCompany> movies) {
        this.movies = movies;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
