package com.example.javasprinboot.controller;

import com.example.javasprinboot.model.User;
import com.example.javasprinboot.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Contr�leur g�rant l'affichage, l'ajout et la modification des utilisateurs.
 * Toutes les routes sont prot�g�es par v�rification de session.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * V�rifie que l'utilisateur est connect�.
     * Redirige vers /login si pas de session active.
     */
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("loggedInUser") != null;
    }

    /**
     * Page : liste de tous les utilisateurs.
     */
    @GetMapping
    public String listUsers(HttpSession session, Model model) {
        if (!isAuthenticated(session)) return "redirect:/login";

        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    /**
     * Page : formulaire d'ajout d'un utilisateur.
     */
    @GetMapping("/add")
    public String addForm(HttpSession session, Model model) {
        if (!isAuthenticated(session)) return "redirect:/login";

        model.addAttribute("user", new User());
        return "add-user";
    }

    /**
     * Action : ajouter un nouvel utilisateur.
     */
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, HttpSession session) {
        if (!isAuthenticated(session)) return "redirect:/login";

        userRepository.save(user);
        return "redirect:/users";
    }

    /**
     * Page : formulaire de modification d'un utilisateur (pr�-rempli).
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAuthenticated(session)) return "redirect:/login";

        model.addAttribute("user", userRepository.findById(id));
        return "edit-user";
    }

    /**
     * Action : modifier un utilisateur.
     */
    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute User user, HttpSession session) {
        if (!isAuthenticated(session)) return "redirect:/login";

        user.setId(id);
        userRepository.update(user);
        return "redirect:/users";
    }

    /**
     * Action : supprimer un utilisateur.
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        if (!isAuthenticated(session)) return "redirect:/login";

        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
