package com.esliceu.movie.DAO;

import com.esliceu.movie.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
    Page<User> findAll(Pageable pageable);

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String encrypedPassword);

    User getUserByUserId(Integer userId);
}
