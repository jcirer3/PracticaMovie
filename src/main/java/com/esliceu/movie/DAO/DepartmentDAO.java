package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentDAO extends JpaRepository<Department, Integer> {
    Page<Department> findAll(Pageable pageable);
    Department findByDepartmentName(String departmentName);
}
