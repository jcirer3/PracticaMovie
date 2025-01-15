package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDAO extends JpaRepository<Person, Long> {
}
