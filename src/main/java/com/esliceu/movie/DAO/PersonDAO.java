package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDAO extends JpaRepository<Person, Integer> {
    Page<Person> findAll(Pageable pageable);
    Person findByPersonName(String personName);
}
