package com.example.javasprinboot.controller;

import com.example.javasprinboot.model.User;
import com.example.javasprinboot.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Contr�leur g�rant la connexion et la d�connexion.
 */
@Controller
public class LoginController {

    /**
     * Redirige la racine vers la page de connexion.
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }



    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Affiche la page de connexion.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Traite la soumission du formulaire de connexion.
     * V�rifie le login/password et cr�e une session si OK.
     */
    @PostMapping("/login")
    public String login(
            @RequestParam String login,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        User user = userRepository.findByLogin(login);

        if (user != null && user.getPassword().equals(password)) {
            // Connexion r�ussie : on stocke l'utilisateur en session
            session.setAttribute("loggedInUser", user);
            return "redirect:/users";
        }

        // Connexion �chou�e
        model.addAttribute("error", "Login ou mot de passe incorrect");
        return "login";
    }

    /**
     * D�connexion : invalide la session et redirige vers le login.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
