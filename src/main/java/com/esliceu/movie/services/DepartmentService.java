package com.esliceu.movie.services;

import com.esliceu.movie.DAO.DepartmentDAO;
import com.esliceu.movie.models.Department;
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
public class DepartmentService {
    @Autowired
    DepartmentDAO departmentDAO;

    public Page<Department> getPaginatedCDepartments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return departmentDAO.findAll(pageable);
    }

    public String saveDepartment(String departmentName) {
        Department existingDepartment = departmentDAO.findByDepartmentName(departmentName);

        if (existingDepartment != null) {
            return "El nombre ya está en uso. Por favor, elija otro.";
        } else {
            // Si no existe, guardar el nuevo department
            Department department = new Department();
            department.setDepartmentName(departmentName);
            departmentDAO.save(department);
            return null;
        }
    }

    public void deleteDepartment(Integer departmentId) {
        Department department = departmentDAO.findById(departmentId).get();
        if (department != null) {
            departmentDAO.delete(department);
        }
    }

    public Department getDepartmentById(Integer departmentId) {
        return departmentDAO.findById(departmentId).get();
    }

    public String updateDepartmentNameById(Integer departmentId, String departmentName) {
        Department existingDepartment = departmentDAO.findByDepartmentName(departmentName);
        if (existingDepartment != null){
            return "El nombre ya está en uso. Por favor, elija otro.";
        }
        Optional<Department> optionalDepartment = departmentDAO.findById(departmentId);

        Department department = optionalDepartment.get();
        department.setDepartmentName(departmentName);
        departmentDAO.save(department);

        return null;
    }

    public String getDepartmentJson() {
        List<Department> departments = departmentDAO.findAll();
        List<String> names = departments.stream()
                .map(d -> d.getDepartmentName())
                .collect(Collectors.toList());

        Gson gson = new Gson();
        String result = gson.toJson(names);
        return result;
    }

    public List<Department> findByName(String departmentName) {
        List<Department> departmentList = new ArrayList<>();
        Department department = departmentDAO.findByDepartmentName(departmentName);
        departmentList.add(department);
        return departmentList;
    }
}
