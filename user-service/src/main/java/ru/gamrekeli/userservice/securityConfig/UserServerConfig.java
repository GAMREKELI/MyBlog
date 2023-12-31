package ru.gamrekeli.userservice.securityConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.gamrekeli.userservice.securityConfig.authorizationComponent.JwtAuthenticationConverterImpl;

@EnableWebSecurity
@Configuration
public class UserServerConfig {

    private static final Logger logger = LoggerFactory.getLogger(UserServerConfig.class);

    @Autowired
    private JwtAuthenticationConverterImpl jwtAuthenticationConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] patterns = new String[] {
                "/api/v1/**",
                "/blog/**",
                "/comment/**"
        };

        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(patterns).hasAuthority("SCOPE_resource.read")
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter);

        logger.info("Security filter chain configured");
        return http.build();
    }
}
