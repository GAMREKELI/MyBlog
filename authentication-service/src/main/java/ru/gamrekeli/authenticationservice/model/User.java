package ru.gamrekeli.authenticationservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(
                name = "email",
                columnNames = "email_address"
        )
)
public class User {

    @Id
    @SequenceGenerator(
            name = "sequence_user",
            sequenceName = "sequence_user",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_user"
    )
    private Long userId;

    private String username;
    private String password;
    @Column(
            name = "email_address",
            nullable = false
    )
    private String email;

//    @Enumerated(EnumType.STRING)
//    private Role role;
}