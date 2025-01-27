package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Authorization;
import com.esliceu.movie.models.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PermissionDAO extends JpaRepository<Permission, Integer> {
    Page<Permission> findAll(Pageable pageable);
    Permission findByName(String keywordPermission);
}
