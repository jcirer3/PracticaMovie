package com.esliceu.movie.services;

import com.esliceu.movie.DAO.GenderDAO;
import com.esliceu.movie.models.Gender;
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
public class GenderService {
    @Autowired
    GenderDAO genderDAO;

    public Page<Gender> getPaginatedGenders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return genderDAO.findAll(pageable);
    }

    public String saveGender(String gender) {
        Gender existingGender = genderDAO.findByGender(gender);

        if (existingGender != null) {
            return "El nombre ya está en uso. Por favor, elija otro.";
        } else {
            // Si no existe, guardar el nuevo género
            Gender newgender = new Gender();
            newgender.setGender(gender);
            genderDAO.save(newgender);
            return null;
        }
    }

    public void deleteGender(Integer genderId) {
        Gender gender = genderDAO.findById(genderId).get();
        if (gender != null) {
            genderDAO.delete(gender);
        }
    }

    public Gender getGenderById(Integer genderId) {
        return genderDAO.findById(genderId).get();
    }

    public String updateGenderById(Integer genderId, String genderName) {
        Gender existingGender = genderDAO.findByGender(genderName);
        if (existingGender != null){
            return "El nombre ya está en uso. Por favor, elija otro.";
        }
        Optional<Gender> optionalGender = genderDAO.findById(genderId);

        Gender gender = optionalGender.get();
        gender.setGender(genderName);
        genderDAO.save(gender);

        return null;
    }

    public String getGenderJson() {
        List<Gender> genders = genderDAO.findAll();
        List<String> names = genders.stream()
                .map(g -> g.getGender())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public List<Gender> findByGender(String genderName) {
        List<Gender> genderList = new ArrayList<>();
        Gender gender = genderDAO.findByGender(genderName);
        genderList.add(gender);
        return genderList;
    }

    public List<Gender> findAll() {
        return genderDAO.findAll();
    }

    public Gender findById(Integer genderId) {
        return genderDAO.findById(genderId).orElse(null);
    }
}
