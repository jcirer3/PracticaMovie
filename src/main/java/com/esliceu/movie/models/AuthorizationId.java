package com.esliceu.movie.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AuthorizationId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "permission_id")
    private Integer permissionId;

    public AuthorizationId() {
    }

    public AuthorizationId(Integer userId, Integer permissionId) {
        this.userId = userId;
        this.permissionId = permissionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationId that = (AuthorizationId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, permissionId);
    }
}