package com.esliceu.movie.services;

import com.esliceu.movie.DAO.CountryDAO;
import com.esliceu.movie.models.Country;
import com.esliceu.movie.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    @Autowired
    CountryDAO countryDAO;

    public Page<Country> getPaginatedPersons(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return countryDAO.findAll(pageable);
    }
}
