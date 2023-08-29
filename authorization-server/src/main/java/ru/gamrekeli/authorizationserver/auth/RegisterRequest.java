package ru.gamrekeli.authorizationserver.auth;

import lombok.*;
import ru.gamrekeli.authorizationserver.model.Role;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private final Role role = Role.USER;
}
