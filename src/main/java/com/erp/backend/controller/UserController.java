package com.erp.backend.controller;

import com.erp.backend.entity.User;
import com.erp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService service;

    // ✅ Injection via constructeur (recommandée)
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    // ✅ Récupérer tous les utilisateurs
    @GetMapping
    public List<User> all() {
        return service.getAll();
    }

    // ✅ Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Créer un nouvel utilisateur
    @PostMapping
    public User create(@RequestBody User u) {
        return service.create(u);
    }

    // ✅ Mettre à jour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User u) {
        return service.getById(id)
                .map(existing -> ResponseEntity.ok(service.update(id, u)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
