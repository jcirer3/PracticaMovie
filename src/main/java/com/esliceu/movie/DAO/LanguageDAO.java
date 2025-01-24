package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageDAO extends JpaRepository<Language, Integer> {
    Page<Language> findAll(Pageable pageable);
    Language findByLanguageName(String languageName);
}
