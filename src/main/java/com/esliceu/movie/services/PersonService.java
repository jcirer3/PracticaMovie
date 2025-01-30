package com.esliceu.movie.services;

import com.esliceu.movie.DAO.MovieCastDAO;
import com.esliceu.movie.DAO.MovieCrewDAO;
import com.esliceu.movie.DAO.PersonDAO;
import com.esliceu.movie.models.MovieCast;
import com.esliceu.movie.models.MovieCastId;
import com.esliceu.movie.models.MovieCrewId;
import com.esliceu.movie.models.Person;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    PersonDAO personDAO;
    @Autowired
    MovieCrewDAO movieCrewDAO;
    @Autowired
    MovieCastDAO movieCastDAO;

    public Page<Person> getPaginatedPersons(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return personDAO.findAll(pageable);
    }

    public String savePerson(String personName) {
        Person existingPerson = personDAO.findByPersonName(personName);

        if (existingPerson != null) {
            return "El nombre ya está en uso. Por favor, elija otro.";
        } else {
            // Si no existe, guardar la nueva persona
            Person person = new Person();
            person.setPersonName(personName);
            personDAO.save(person);
            return null;
        }
    }

    public void deletePerson(Integer personId) {
        Person person = personDAO.findById(personId).get();
        if (person != null) {
            personDAO.delete(person);
        }
    }

    public Person getPersonById(Integer personId) {
        return personDAO.findById(personId).get();
    }

    public String updatePersonNameById(Integer personId, String personName) {
        Person existingPerson = personDAO.findByPersonName(personName);
        if (existingPerson != null){
            return "El nombre ya está en uso. Por favor, elija otro.";
        }
        Optional<Person> optionalPerson = personDAO.findById(personId);

        Person person = optionalPerson.get();
        person.setPersonName(personName);
        personDAO.save(person);

        return null;
    }

    public String getPersonJson() {
        List<Person> persons = personDAO.findAll();
        List<String> names = persons.stream()
                .map(p -> p.getPersonName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public List<Person> findByName(String personName) {
        List<Person> personList = new ArrayList<>();
        Person person = personDAO.findByPersonName(personName);
        personList.add(person);
        return personList;
    }

    public void deleteMovieCrew(MovieCrewId movieCrewId) {
        movieCrewDAO.deleteById(movieCrewId);
    }

    public Person findByPersonName(String personName) {
        return personDAO.findByPersonName(personName);
    }

    public void deleteMovieCast(Integer movieId, Integer personId, Integer genderId) {
        MovieCast movieCast = movieCastDAO.findByMovie_MovieIdAndPerson_PersonIdAndGender_GenderId(movieId, personId, genderId);
        movieCastDAO.delete(movieCast);
    }
}
