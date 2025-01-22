package com.esliceu.movie.services;

import com.esliceu.movie.DAO.CountryDAO;
import com.esliceu.movie.models.Country;
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
public class CountryService {
    @Autowired
    CountryDAO countryDAO;

    public Page<Country> getPaginatedCountrys(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return countryDAO.findAll(pageable);
    }

    public String saveCountry(String countryName) {
        Country existingCountry = countryDAO.findByCountryName(countryName);

        if (existingCountry != null) {
            return "El nombre ya está en uso. Por favor, elija otro.";
        } else {
            // Si no existe, guardar el nuevo país
            Country country = new Country();
            country.setCountryName(countryName);
            countryDAO.save(country);
            return null;
        }
    }

    public void deleteCountry(Integer countryId) {
        Country country = countryDAO.findById(countryId).get();
        if (country != null) {
            countryDAO.delete(country);
        }
    }

    public Country getCountryById(Integer countryId) {
        return countryDAO.findById(countryId).get();
    }

    public String updateCountryNameById(Integer countryId, String countryName) {
        Country existingCountry = countryDAO.findByCountryName(countryName);
        if (existingCountry != null){
            return "El nombre ya está en uso. Por favor, elija otro.";
        }
        Optional<Country> optionalCountry = countryDAO.findById(countryId);

        Country country = optionalCountry.get();
        country.setCountryName(countryName);
        countryDAO.save(country);

        return null;
    }

    public String getCountryJson() {
        List<Country> countrys = countryDAO.findAll();
        List<String> names = countrys.stream()
                .map(c -> c.getCountryName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public List<Country> findByName(String countryName) {
        List<Country> countryList = new ArrayList<>();
        Country country = countryDAO.findByCountryName(countryName);
        countryList.add(country);
        return countryList;
    }
}
