package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryDAO extends JpaRepository<Country, Integer> {
    Page<Country> findAll(Pageable pageable);
    Country findByCountryName(String countryName);
}
