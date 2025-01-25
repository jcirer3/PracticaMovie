package com.esliceu.movie.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personId;

    @Column(length = 500)
    private String personName;

    @OneToMany(mappedBy = "person")
    Set<MovieCast> characterName;

    @OneToMany(mappedBy = "person")
    Set<MovieCast> castOrder;

    @OneToMany(mappedBy = "person")
    Set<MovieCrew> job;

    public Set<MovieCrew> getJob() {
        return job;
    }

    public void setJob(Set<MovieCrew> job) {
        this.job = job;
    }

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

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
