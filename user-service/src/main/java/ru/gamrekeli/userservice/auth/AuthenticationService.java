package ru.gamrekeli.userservice.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gamrekeli.userservice.jwtconfiguration.JwtService;
import ru.gamrekeli.userservice.model.Role;
import ru.gamrekeli.userservice.model.User;
import ru.gamrekeli.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;
    public AuthenticationResponce register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponce.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponce authenticate(AuthenticationRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponce.builder()
                .token(jwtToken)
                .build();
    }
}
