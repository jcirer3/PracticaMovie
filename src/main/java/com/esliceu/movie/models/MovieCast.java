package com.esliceu.movie.models;

import jakarta.persistence.*;

@Entity
public class MovieCast {

    @EmbeddedId
    private MovieCastId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId") // Mapea la propiedad movieId de MovieCompanyId con la columna movieId
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("genderId") // Mapea la propiedad companyId de MovieCompanyId con la columna companyId
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("personId") // Mapea la propiedad companyId de MovieCompanyId con la columna companyId
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(length = 400)
    private String characterName;
    private Integer castOrder;

    public MovieCastId getId() {
        return id;
    }

    public void setId(MovieCastId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Integer getCastOrder() {
        return castOrder;
    }

    public void setCastOrder(Integer castOrder) {
        this.castOrder = castOrder;
    }
}
