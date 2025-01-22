package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderDAO extends JpaRepository<Gender, Integer> {
    Page<Gender> findAll(Pageable pageable);
    Gender findByGender(String gender);
}
