package com.esliceu.movie.controllers;

import com.esliceu.movie.exceptions.PasswordToShortException;
import com.esliceu.movie.exceptions.UserAlreadyExistException;
import com.esliceu.movie.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @GetMapping("/register")
    public String showRegisterPage(){
        return "register";
    }

    @PostMapping("/perform-register")
    public String performRegister (@RequestParam String username, @RequestParam String password, Model model){
        try{
            System.out.println("registrando");
            registerService.saveUser(username, password);
            return "redirect:/login";
        } catch (PasswordToShortException | UserAlreadyExistException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        } catch (Exception e){
            model.addAttribute("error", "Error inesperado. Vuelve a intentarlo.");
            return "register";
        }
    }
}
