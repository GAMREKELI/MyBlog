package ru.gamrekeli.userservice.securityConfig.authorizationComponent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import ru.gamrekeli.userservice.model.User;
import ru.gamrekeli.userservice.repository.UserRepository;


@Component
@Slf4j
public class SecurityComponent {

    @Autowired
    private UserRepository repository;


    public boolean checkUserByUserId(Authentication authentication, Long userId) {

        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwtToken = (Jwt) authentication.getPrincipal();
            String userIdFromToken = jwtToken.getClaim("sub").toString();

            log.info("************" + userIdFromToken + "************");

            User checkUser = repository.findByUsername(userIdFromToken).orElse(null);

            // Делаю проверку что авторизованный пользователь есть в базе и его id совпадает с id запроса.
            return checkUser != null && checkUser.getUserId().equals(userId);
        }
        return false;
    }
}
