package com.esliceu.movie.DAO;

import com.esliceu.movie.models.Authorization;
import com.esliceu.movie.models.AuthorizationId;
import com.esliceu.movie.models.AuthorizationState;
import com.esliceu.movie.models.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AuthorizationDAO extends JpaRepository<Authorization, AuthorizationId> {
    Page<Authorization> findAll(Pageable pageable);
    Set<Authorization> findByUser_UserId(Integer userId);



    @Query("SELECT a FROM Authorization a WHERE a.user.userId = :userId AND a.permission.permissionId = :permissionId")
    Authorization findByUserIdAndPermissionId(@Param("userId") Integer userId, @Param("permissionId") Integer permissionId);

    List<Authorization> findByState(AuthorizationState authorizationState);
}
