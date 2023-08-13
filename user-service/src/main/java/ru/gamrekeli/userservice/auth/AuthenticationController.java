package ru.gamrekeli.userservice.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @GetMapping("/authenticate")
    public String authenticateUser(@ModelAttribute("request") AuthenticationRequest request) {
        return "login";
    }

    @GetMapping("/register")
    public String registerUser(@ModelAttribute("request") RegisterRequest request) {
        return "singUp";
    }

    @PostMapping("/register")
    public String register (
            @ModelAttribute("request") RegisterRequest request
    ) {

        service.register(request);
        return "redirect:/api/v1/auth/authenticate";
    }

    @PostMapping("/authenticate")
    public String register (
            @ModelAttribute("request") AuthenticationRequest request
    ) {
//        System.out.println(request.getUsername());
//        System.out.println(request.getPassword());
        System.out.println("LOOOOLLLLLL");
        service.authenticate(request);
        return "redirect:/api/v1/auth/authenticate";
    }
}
