package ru.gamrekeli.userservice.securityConfig.authorizationComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import ru.gamrekeli.userservice.model.User;
import ru.gamrekeli.userservice.repository.UserRepository;


@Component
public class SecurityComponent {

    @Autowired
    private UserRepository repository;

    public boolean checkUserByUserId(Authentication authentication, Long userId) {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
            Jwt jwt = (Jwt) token.getCredentials(); // Assuming the token is stored in credentials

            String username = jwt.getClaim("preferred_username"); // Extract username claim from JWT
            User checkUser = repository.findByUsername(username).orElse(null);

            // Делаю проверку что авторизованный пользователь есть в базе и его id совпадает с id запроса.
            return checkUser != null && checkUser.getUserId().equals(userId);
        }
        return false;
    }
}
