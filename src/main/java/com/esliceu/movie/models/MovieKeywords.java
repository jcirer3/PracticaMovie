package com.esliceu.movie.models;

import jakarta.persistence.*;

@Entity
public class MovieKeywords {

    @EmbeddedId
    private MovieKeywordsId id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @MapsId("keywordId")
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    public MovieKeywordsId getId() {
        return id;
    }

    public void setId(MovieKeywordsId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }
}
