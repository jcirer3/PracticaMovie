package com.esliceu.movie.DAO;

import com.esliceu.movie.models.LanguageRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRoleDAO extends JpaRepository<LanguageRole, Integer> {
    Page<LanguageRole> findAll(Pageable pageable);
    LanguageRole findByLanguageRole(String languageRole);
}
