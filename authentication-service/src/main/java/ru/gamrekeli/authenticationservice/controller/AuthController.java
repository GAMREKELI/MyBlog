package ru.gamrekeli.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.authenticationservice.dto.AuthRequest;
import ru.gamrekeli.authenticationservice.dto.RegisterRequest;
import ru.gamrekeli.authenticationservice.model.User;
import ru.gamrekeli.authenticationservice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String registerUser(@ModelAttribute("request") User user) {
        return "singUp";
    }

    @PostMapping("/register")
    public String addNewUser(@ModelAttribute("request") User user) {
        return service.saveUser(user);
    }

    @GetMapping("/token")
    public String authenticateUser(@ModelAttribute("request") AuthRequest authRequest) {
        return "login";
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return service.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}
