package com.esliceu.movie.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(length = 1000)
    private String title;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    Set<MovieCompany> companies;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    Set<MovieGenres> genres;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    Set<MovieCast> characterName;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    Set<MovieCast> castOrder;

    public Set<MovieCast> getCharacterName() {
        return characterName;
    }

    public void setCharacterName(Set<MovieCast> characterName) {
        this.characterName = characterName;
    }

    public Set<MovieCast> getCastOrder() {
        return castOrder;
    }

    public void setCastOrder(Set<MovieCast> castOrder) {
        this.castOrder = castOrder;
    }

    public Set<MovieGenres> getGenres() {
        return genres;
    }

    public void setGenres(Set<MovieGenres> genres) {
        this.genres = genres;
    }

    public Set<MovieCompany> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<MovieCompany> companies) {
        this.companies = companies;
    }

    private Integer budget;

    @Column(length = 1000)
    private String homepage;

    @Column(length = 1000)
    private String overview;

    @Column(precision = 12, scale = 6)
    private BigDecimal popularity;

    private LocalDate releaseDate;
    private BigInteger revenue;
    private Integer runtime;

    @Column(length = 50)
    private String movieStatus;

    @Column(length = 1000)
    private String tagline;

    @Column(precision = 4, scale = 2)
    private BigDecimal voteAverage;

    private Integer voteCount;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public BigDecimal getPopularity() {
        return popularity;
    }

    public void setPopularity(BigDecimal popularity) {
        this.popularity = popularity;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigInteger getRevenue() {
        return revenue;
    }

    public void setRevenue(BigInteger revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(String movieStatus) {
        this.movieStatus = movieStatus;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public BigDecimal getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(BigDecimal voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
