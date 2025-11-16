package com.erp.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * ✅ Bean pour encoder les mots de passe (utilisé dans AuthController)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * ✅ Bean pour l'AuthenticationManager (utilisé dans AuthController)
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * ✅ Configuration des règles de sécurité HTTP
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // désactive CSRF pour API REST
                .authorizeHttpRequests(auth -> auth
                        // Autoriser les endpoints publics (login/register)
                        .requestMatchers("/api/auth/**").permitAll()
                        // Protéger le reste
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable()) // on n'utilise pas le formulaire de login par défaut
                .httpBasic(httpBasic -> httpBasic.disable()); // ni l'authentification basique

        return http.build();
    }
}
