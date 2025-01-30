package com.esliceu.movie.services;

import com.esliceu.movie.DAO.AuthorizationDAO;
import com.esliceu.movie.DAO.PermissionDAO;
import com.esliceu.movie.DAO.UserDAO;
import com.esliceu.movie.models.Authorization;
import com.esliceu.movie.models.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PermissionService {
    @Autowired
    AuthorizationDAO authorizationDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    PermissionDAO permissionDAO;


    public List<Permission> getAllPermissions() {
        return permissionDAO.findAll();
    }
}
