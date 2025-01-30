package com.esliceu.movie.services;

import com.esliceu.movie.DAO.AuthorizationDAO;
import com.esliceu.movie.DAO.PermissionDAO;
import com.esliceu.movie.DAO.UserDAO;
import com.esliceu.movie.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Permission findPermissionByName(String keywordPermission) {
        return permissionDAO.findByName(keywordPermission);
    }

    public Authorization findAuthorizationByIds(Integer userId, Integer permissionId) {
        return authorizationDAO.findByUserIdAndPermissionId(userId, permissionId);
    }

    public void requestPermission(User user, Integer permissionId) {
        AuthorizationId id = new AuthorizationId(user.getUserId(), permissionId);
        if (authorizationDAO.findById(id).isPresent()) {
            return; // Ya existe la solicitud o el permiso
        }
        Permission permission = permissionDAO.findById(permissionId).orElseThrow();
        Authorization authorization = new Authorization();
        authorization.setId(id);
        authorization.setUser(user);
        authorization.setPermission(permission);
        authorization.setState(AuthorizationState.PENDING);
        authorizationDAO.save(authorization);
    }

    public List<Authorization> getPendingAuthorizations() {
        return authorizationDAO.findByState(AuthorizationState.PENDING);
    }

    public void updateAuthorizationState(Integer userId, Integer permissionId, AuthorizationState state) {
        AuthorizationId id = new AuthorizationId(userId, permissionId);
        Authorization authorization = authorizationDAO.findById(id).orElseThrow();
        authorization.setState(state);
        authorizationDAO.save(authorization);
    }
}
