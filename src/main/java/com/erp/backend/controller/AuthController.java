package com.erp.backend.controller;

import com.erp.backend.dto.LoginRequest;
import com.erp.backend.dto.RegisterRequest;
import com.erp.backend.entity.ERole;
import com.erp.backend.entity.Role;
import com.erp.backend.entity.User;
import com.erp.backend.repository.RoleRepository;
import com.erp.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * 🔐 Endpoint de connexion (Login)
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Validation des champs
            if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Le nom d'utilisateur est requis");
            }

            if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Le mot de passe est requis");
            }

            // Recherche de l'utilisateur
            Optional<User> userOpt = userService.findByUsername(loginRequest.getUsername());

            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("❌ Utilisateur non trouvé");
            }

            User user = userOpt.get();

            // Vérification du mot de passe
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("❌ Mot de passe incorrect");
            }

            // Réponse de succès
            return ResponseEntity.ok(Map.of(
                    "message", "✅ Connexion réussie",
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "fullName", user.getFullName(),
                    "userId", user.getId()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Erreur serveur : " + e.getMessage());
        }
    }

    /**
     * 🔑 Endpoint d'inscription (Register) - CORRIGÉ
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            System.out.println("Tentative d'inscription: " + registerRequest.getUsername());

            // Vérification des champs requis
            if (registerRequest.getUsername() == null || registerRequest.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Le nom d'utilisateur est requis");
            }

            if (registerRequest.getPassword() == null || registerRequest.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Le mot de passe est requis");
            }

            if (registerRequest.getEmail() == null || registerRequest.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("❌ L'email est requis");
            }

            // Vérifie si l'utilisateur existe déjà
            if (userService.existsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.badRequest().body("❌ Nom d'utilisateur déjà pris !");
            }

            // Vérifie si l'email existe déjà
            if (userService.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest().body("❌ Email déjà utilisé !");
            }

            // Création du nouvel utilisateur
            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            newUser.setEmail(registerRequest.getEmail());
            newUser.setFullName(registerRequest.getFullName());
            newUser.setRoles(new HashSet<>());

            // Assignation du rôle USER par défaut
            Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
            if (userRole.isPresent()) {
                newUser.getRoles().add(userRole.get());
            } else {
                // Si le rôle n'existe pas, créer un rôle par défaut
                Role defaultRole = new Role();
                defaultRole.setName(ERole.ROLE_USER);
                Role savedRole = roleRepository.save(defaultRole);
                newUser.getRoles().add(savedRole);
            }

            // Sauvegarde dans la base
            User savedUser = userService.save(newUser);

            if (savedUser == null || savedUser.getId() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("❌ Erreur lors de la création de l'utilisateur");
            }

            return ResponseEntity.ok(Map.of(
                    "message", "✅ Utilisateur créé avec succès",
                    "userId", savedUser.getId(),
                    "username", savedUser.getUsername(),
                    "email", savedUser.getEmail()
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Erreur serveur : " + e.getMessage());
        }
    }

    /**
     * 🔍 Endpoint pour lister tous les utilisateurs (pour test)
     */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Erreur lors de la récupération des utilisateurs");
        }
    }
}