package com.esliceu.movie.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer keywordId;

    @Column(length = 100)
    private String keywordName;

    @OneToMany(mappedBy = "keyword")
    private Set<MovieKeywords> movies;

    public Set<MovieKeywords> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieKeywords> movies) {
        this.movies = movies;
    }

    public Integer getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Integer keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }
}
