package com.esliceu.movie.services;

import com.esliceu.movie.DAO.PersonDAO;
import com.esliceu.movie.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    PersonDAO personDAO;

    public Page<Person> getPaginatedPersons(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return personDAO.findAll(pageable);
    }

    public void savePerson(String personName) {
        Person person = new Person();
        person.setPersonName(personName);
        personDAO.save(person);
    }

    public void deletePerson(Integer personId) {
        Person person = personDAO.findById(personId).get();
        if (person != null) {
            personDAO.delete(person);
        }
    }

    public Person getPersonByName(String personName) {
        return personDAO.findByPersonName(personName);
    }

    public void updatePersonName(String oldPersonName, String newPersonName) {
        Person person = personDAO.findByPersonName(oldPersonName);
        if (person != null) {
            person.setPersonName(newPersonName);
            personDAO.save(person);
        }
    }
}
