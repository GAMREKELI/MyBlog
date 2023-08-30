package ru.gamrekeli.authorizationserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gamrekeli.authorizationserver.auth.AuthenticationRequest;
import ru.gamrekeli.authorizationserver.auth.RegisterRequest;
import ru.gamrekeli.authorizationserver.model.User;
import ru.gamrekeli.authorizationserver.repository.UserRepository;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
//
////    @GetMapping("/login")
////    public String authenticateUser(@ModelAttribute("request") AuthenticationRequest request) {
////        return "login";
////    }
//
    @GetMapping("/register")
    public String registerUser(@ModelAttribute("request") RegisterRequest request) {
        return "singUp";
    }

    @PostMapping("/register")
    public String register (
            @ModelAttribute("request") RegisterRequest request
    ) {

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(request.getRole())
                .build();
        repository.save(user);
        return "redirect:/login";
    }
//
////    @PostMapping("/login")
////    public void register (
////            @ModelAttribute("request") AuthenticationRequest request
////    ) {
////
////    }
}
