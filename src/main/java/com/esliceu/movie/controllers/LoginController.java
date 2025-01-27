    package com.esliceu.movie.controllers;

    import com.esliceu.movie.models.User;
    import com.esliceu.movie.services.LoginService;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestParam;

    @Controller
    public class LoginController {
        @Autowired
        LoginService loginService;

        @GetMapping("/login")
        public String showLoginPage() {
            return "login";
        }
        @GetMapping("/logout")
        public String logout(HttpSession session) {
            session.invalidate();
            return "redirect:/login";
        }
        @PostMapping("/login")
        public String performLogin(@RequestParam String username, @RequestParam String password,
                HttpSession session, Model model) {
            try {
                User user = loginService.authenticate(username, password);

                if (user != null) {
                    session.setAttribute("user", user);
                    return "redirect:/home";
                }
            } catch (Exception e) {
                model.addAttribute("error", "Has introducido mal los datos.");
                return "login";
            }
            return "login";
        }
    }
