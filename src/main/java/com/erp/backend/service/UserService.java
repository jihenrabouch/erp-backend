package com.erp.backend.service;

import com.erp.backend.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    Optional<User> getById(Long id);
    User create(User user);
    User update(Long id, User user);
    void delete(Long id);

    // Pour authentification
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email); // ✅ AJOUTEZ CETTE MÉTHODE

    User save(User newUser);
}