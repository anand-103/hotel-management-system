package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.model.User;
import com.example.hotelmanagement.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Show Authentication Page (Login & Signup)
    @GetMapping("/auth")
    public String showAuthPage(Model model) {
        model.addAttribute("user", new User());
        return "auth";
    }

    // Register User
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already exists!");
            return "auth";
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        model.addAttribute("success", "Registration successful! Please log in.");
        return "auth"; // Stay on the same page with a success message
    }

    // Login User (Handled by Spring Security)
    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model, HttpSession session) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty() || !passwordEncoder.matches(password, userOptional.get().getPassword())) {
            model.addAttribute("error", "Invalid email or password!");
            return "auth";
        }

        session.setAttribute("loggedInUser", userOptional.get());
        return "redirect:/home"; // Redirect to home page after successful login
    }

    // Logout (Handled by Spring Security)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth?logout=true";
    }
}
