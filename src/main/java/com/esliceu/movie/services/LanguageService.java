package com.esliceu.movie.services;

import com.esliceu.movie.DAO.LanguageDAO;
import com.esliceu.movie.DAO.MovieLanguageDAO;
import com.esliceu.movie.models.Language;
import com.esliceu.movie.models.MovieLanguagesId;
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
public class LanguageService {
    @Autowired
    LanguageDAO languageDAO;
    @Autowired
    MovieLanguageDAO movieLanguageDAO;

    public Page<Language> getPaginatedLanguages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return languageDAO.findAll(pageable);
    }

    public String saveLanguage(String languageName) {
        Language existingLanguage = languageDAO.findByLanguageName(languageName);

        if (existingLanguage != null) {
            return "El nombre ya está en uso. Por favor, elija otro.";
        } else {
            // Si no existe, guardar el nuevo idioma
            Language language = new Language();
            language.setLanguageName(languageName);
            languageDAO.save(language);
            return null;
        }
    }

    public void deleteLanguage(Integer languageId) {
        Language language = languageDAO.findById(languageId).get();
        if (language != null) {
            languageDAO.delete(language);
        }
    }

    public Language getLanguageById(Integer languageId) {
        return languageDAO.findById(languageId).get();
    }

    public String updateLanguageNameById(Integer languageId, String languageName) {
        Language existingLanguage = languageDAO.findByLanguageName(languageName);
        if (existingLanguage != null){
            return "El nombre ya está en uso. Por favor, elija otro.";
        }
        Optional<Language> optionalLanguage = languageDAO.findById(languageId);

        Language language = optionalLanguage.get();
        language.setLanguageName(languageName);
        languageDAO.save(language);

        return null;
    }

    public String getLanguageJson() {
        List<Language> languages = languageDAO.findAll();
        List<String> names = languages.stream()
                .map(l -> l.getLanguageName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public List<Language> findByName(String languageName) {
        List<Language> languageList = new ArrayList<>();
        Language language = languageDAO.findByLanguageName(languageName);
        languageList.add(language);
        return languageList;
    }

    public void deleteMovieLanguage(MovieLanguagesId movieLanguagesId) {
        movieLanguageDAO.deleteById(movieLanguagesId);
    }

    public Language findByLanguageName(String languageName) {
        return languageDAO.findByLanguageName(languageName);
    }
}
