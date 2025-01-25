package com.esliceu.movie.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genderId;

    @Column(length = 20)
    private String gender;

    @OneToMany(mappedBy = "gender", fetch = FetchType.LAZY)
    Set<MovieCast> characterName;

    @OneToMany(mappedBy = "gender", fetch = FetchType.LAZY)
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

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
