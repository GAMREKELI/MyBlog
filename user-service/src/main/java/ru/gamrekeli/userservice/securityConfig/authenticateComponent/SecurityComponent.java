package ru.gamrekeli.userservice.securityConfig.authenticateComponent;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.gamrekeli.userservice.model.User;
import ru.gamrekeli.userservice.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class SecurityComponent {

    private final UserRepository repository;

    public boolean checkUserByUserId(Authentication authentication, Long userId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User checkUser = repository.findByUsername(userDetails.getUsername()).orElse(null);

        // Делаю проверку что авторизованный пользователь есть в базе и его id совпадает с id запроса.
        return checkUser != null && checkUser.getUserId().equals(userId);
    }

}
