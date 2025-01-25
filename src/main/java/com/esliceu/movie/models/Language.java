package com.esliceu.movie.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer languageId;

    @Column(length = 10)
    private String languageCode;

    @Column(length = 500)
    private String languageName;

    @OneToMany(mappedBy = "language")
    private Set<MovieLanguages> movies;

    public Set<MovieLanguages> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieLanguages> movies) {
        this.movies = movies;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
