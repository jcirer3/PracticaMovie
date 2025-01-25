package com.esliceu.movie.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class LanguageRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(length = 20)
    private String languageRole;

    @OneToMany(mappedBy = "languageRole")
    private Set<MovieLanguages> movies;

    public Set<MovieLanguages> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieLanguages> movies) {
        this.movies = movies;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getLanguageRole() {
        return languageRole;
    }

    public void setLanguageRole(String languageRole) {
        this.languageRole = languageRole;
    }
}
