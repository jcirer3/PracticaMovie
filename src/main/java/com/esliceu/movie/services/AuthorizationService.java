package com.esliceu.movie.services;

import com.esliceu.movie.DAO.AuthorizationDAO;
import com.esliceu.movie.DAO.PermissionDAO;
import com.esliceu.movie.DAO.UserDAO;
import com.esliceu.movie.models.Authorization;
import com.esliceu.movie.models.Permission;
import com.esliceu.movie.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthorizationService {
    @Autowired
    AuthorizationDAO authorizationDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    PermissionDAO permissionDAO;

    public Set<Authorization> getPermissionsByUserId(Integer userId) {
        return authorizationDAO.findByUser_UserId(userId);
    }

    public User getUserById(Integer userId) {
        return userDAO.getUserByUserId(userId);
    }

    public Permission findPermissionByName(String keywordPermission) {
        return permissionDAO.findByName(keywordPermission);
    }
}
