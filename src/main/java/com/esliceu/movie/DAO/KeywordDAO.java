package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordDAO extends JpaRepository<Keyword, Integer> {
    Page<Keyword> findAll(Pageable pageable);
    Keyword findByKeywordName(String keywordName);
}
