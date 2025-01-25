package com.esliceu.movie.models;

import jakarta.persistence.*;

@Entity
public class MovieLanguages {

    @EmbeddedId
    private MovieLanguagesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("languageId")
    @JoinColumn(name = "language_id")
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("languageRoleId")
    @JoinColumn(name = "language_role_id")
    private LanguageRole languageRole;

    public MovieLanguagesId getId() {
        return id;
    }

    public void setId(MovieLanguagesId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LanguageRole getLanguageRole() {
        return languageRole;
    }

    public void setLanguageRole(LanguageRole languageRole) {
        this.languageRole = languageRole;
    }
}
