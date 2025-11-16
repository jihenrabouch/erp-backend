package com.erp.backend.controller;

import com.erp.backend.dto.JwtResponse;
import com.erp.backend.dto.LoginRequest;
import com.erp.backend.entity.User;
import com.erp.backend.security.JwtTokenProvider;
import com.erp.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Autorise les appels depuis Angular, React, etc.
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 🔐 Endpoint de connexion (Login)
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 🔹 Authentification de l’utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // 🔹 Récupération de l’utilisateur depuis la base
        User user = userService.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // 🔹 Extraction du rôle (si un seul rôle principal)
        String roleName = user.getRoles().stream()
                .findFirst()
                .map(role -> role.getName())
                .orElse("USER");

        // 🔹 Génération du token JWT
        String token = tokenProvider.createToken(user.getUsername(), roleName);

        // 🔹 Réponse avec token et nom d'utilisateur
        return ResponseEntity.ok(new JwtResponse(token, user.getUsername()));
    }

    /**
     * 🔑 Endpoint d’inscription (Register)
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        // 🔹 Vérifie si l’utilisateur existe déjà
        if (userService.existsByUsername(newUser.getUsername())) {
            return ResponseEntity.badRequest().body("❌ Nom d'utilisateur déjà pris !");
        }

        // 🔹 Encode le mot de passe avant sauvegarde
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // 🔹 Sauvegarde dans la base
        User savedUser = userService.save(newUser);

        return ResponseEntity.ok("✅ Utilisateur créé avec succès : " + savedUser.getUsername());
    }
}
