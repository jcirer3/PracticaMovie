package com.esliceu.movie.services;

import com.esliceu.movie.DAO.UserDAO;
import com.esliceu.movie.exceptions.PasswordToShortException;
import com.esliceu.movie.exceptions.UserAlreadyExistException;
import com.esliceu.movie.models.User;
import com.esliceu.movie.utils.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class RegisterService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    Encrypter encrypter;

    public void saveUser(String username, String password) throws UserAlreadyExistException, PasswordToShortException, NoSuchAlgorithmException {
        if (validaUser(username) && validaPassword(password)){
            password = encrypter.encryptedString(password);
            User user = new User();
            user.setUserName(username);
            user.setPassword(password);
            user.setAdministrador(false);
            userDAO.save(user);
        } else {
            System.out.println("Error, no se ha creado el usuario");
        }
    }
    public boolean validaPassword(String password) throws PasswordToShortException {
        if (password == null || password.length() < 5) {
            throw new PasswordToShortException("La contrasenya és massa curta. Ha de tenir mínim 5 caràcters.");
        }
        System.out.println("es true password");
        return true;
    }

    public boolean validaUser(String username) throws UserAlreadyExistException {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getUserName() != null) {
            throw new UserAlreadyExistException("El nom d'usuari ja existeix.");
        }
        System.out.println("Es true user");
        return true;
    }
}
