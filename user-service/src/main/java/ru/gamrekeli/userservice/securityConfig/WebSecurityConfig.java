package ru.gamrekeli.userservice.securityConfig;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.gamrekeli.userservice.jwtconfiguration.JwtAuthenticationFilter;
import ru.gamrekeli.userservice.model.User;
import ru.gamrekeli.userservice.repository.UserRepository;
import ru.gamrekeli.userservice.securityConfig.authenticateComponent.SecurityComponent;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/{userId}/**").access("@mySecurity.checkUserByUserId(authentication, #userId)")
                    .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                    .formLogin((form) -> form
                            .loginPage("/api/v1/auth/authenticate")
                            .successHandler(((request, response, authentication) -> {
                                String Username = authentication.getName();
                                String targetUrl = "/api/v1/with-blogs/" + userRepository.findByUsername(Username)
                                        .map(User::getUserId).orElse(null); // пофиксить null.
                                response.sendRedirect(targetUrl);
                            }))
                            .permitAll())
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
