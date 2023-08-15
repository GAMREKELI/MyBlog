package ru.gamrekeli.userservice.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gamrekeli.userservice.repository.UserRepository;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserRepository userRepository;

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
    public void register (
            @ModelAttribute("request") AuthenticationRequest request
    ) {
        service.authenticate(request);
//        return "redirect:/api/v1/auth/authenticate";
//        return "redirect:/api/v1/with-blogs/" + userRepository.findUserIdByUsername(request.getUsername());
    }
}
