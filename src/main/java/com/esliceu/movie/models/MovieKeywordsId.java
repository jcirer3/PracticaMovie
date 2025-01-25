package com.esliceu.movie.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MovieKeywordsId implements Serializable {
    @Column(name = "movie_id")
    private Integer movieId;
    @Column(name = "keyword_id")
    private Integer keywordId;

    public MovieKeywordsId() {
    }

    public MovieKeywordsId(Integer movieId, Integer keywordId) {
        this.movieId = movieId;
        this.keywordId = keywordId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(Integer keywordId) {
        this.keywordId = keywordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieKeywordsId that = (MovieKeywordsId) o;
        return Objects.equals(movieId, that.movieId) && Objects.equals(keywordId, that.keywordId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, keywordId);
    }
}
