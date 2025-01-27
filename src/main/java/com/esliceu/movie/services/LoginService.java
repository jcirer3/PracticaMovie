package com.esliceu.movie.services;

import com.esliceu.movie.DAO.UserDAO;
import com.esliceu.movie.models.User;
import com.esliceu.movie.utils.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class LoginService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    Encrypter encrypter;

    public User authenticate(String username, String password) throws NoSuchAlgorithmException {
        String encrypedPassword = encrypter.encryptedString(password);
        return userDAO.findByUsernameAndPassword(username, encrypedPassword);
    }
}
