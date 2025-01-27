package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Authorization;
import com.esliceu.movie.models.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AuthorizationDAO extends JpaRepository<Authorization, Integer> {
    Page<Authorization> findAll(Pageable pageable);
    Set<Authorization> findByUser_UserId(Integer userId);
}
