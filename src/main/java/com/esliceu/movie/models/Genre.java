package com.esliceu.movie.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreId;

    @Column(length = 100)
    private String genreName;

    @OneToMany(mappedBy = "genre")
    private Set<MovieGenres> movies;

    public Set<MovieGenres> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieGenres> movies) {
        this.movies = movies;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
