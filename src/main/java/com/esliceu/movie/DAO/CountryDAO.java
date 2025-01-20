package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryDAO extends JpaRepository<Country, Integer> {
}
