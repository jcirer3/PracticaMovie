package com.esliceu.movie.services;

import com.esliceu.movie.DAO.LanguageRoleDAO;
import com.esliceu.movie.models.LanguageRole;
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
public class LanguageRoleService {
    @Autowired
    LanguageRoleDAO languageRoleDAO;

    public Page<LanguageRole> getPaginatedLanguagesRole(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return languageRoleDAO.findAll(pageable);
    }

    public String saveLanguageRole(String languageRole) {
        LanguageRole existingLanguageRole = languageRoleDAO.findByLanguageRole(languageRole);

        if (existingLanguageRole != null) {
            return "El nombre ya está en uso. Por favor, elija otro.";
        } else {
            // Si no existe, guardar el nuevo idioma role
            LanguageRole languageRole1 = new LanguageRole();
            languageRole1.setLanguageRole(languageRole);
            languageRoleDAO.save(languageRole1);
            return null;
        }
    }

    public void deleteLanguageRole(Integer roleId) {
        LanguageRole languageRole = languageRoleDAO.findById(roleId).get();
        if (languageRole != null) {
            languageRoleDAO.delete(languageRole);
        }
    }

    public LanguageRole getLanguageById(Integer roleId) {
        return languageRoleDAO.findById(roleId).get();
    }

    public String updateLanguageRoleById(Integer roleId, String languageRole) {
        LanguageRole existingLanguageRole = languageRoleDAO.findByLanguageRole(languageRole);
        if (existingLanguageRole != null){
            return "El nombre ya está en uso. Por favor, elija otro.";
        }
        Optional<LanguageRole> optionalLanguageRole = languageRoleDAO.findById(roleId);

        LanguageRole languageRole1 = optionalLanguageRole.get();
        languageRole1.setLanguageRole(languageRole);
        languageRoleDAO.save(languageRole1);

        return null;
    }

    public String getLanguageJson() {
        List<LanguageRole> languagesRoles = languageRoleDAO.findAll();
        List<String> names = languagesRoles.stream()
                .map(l -> l.getLanguageRole())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public List<LanguageRole> findByName(String languageRole) {
        List<LanguageRole> languageRoleList = new ArrayList<>();
        LanguageRole languageRole1 = languageRoleDAO.findByLanguageRole(languageRole);
        languageRoleList.add(languageRole1);
        return languageRoleList;
    }
}
