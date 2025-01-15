package com.esliceu.movie.services;

import com.esliceu.movie.DAO.PersonDAO;
import com.esliceu.movie.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    PersonDAO personDAO;

    public List<Person> getPersons(){
        return personDAO.findAll();
    }

    public void savePerson(String personName) {
        Person person = new Person();
        person.setPersonName(personName);
        personDAO.save(person);
    }
}
