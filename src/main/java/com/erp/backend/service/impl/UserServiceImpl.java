package com.erp.backend.service.impl;

import com.erp.backend.entity.User;
import com.erp.backend.repository.UserRepository;
import com.erp.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return repo.findById(id);
    }

    @Override
    public User create(User user) {
        return repo.save(user);
    }

    @Override
    public User update(Long id, User user) {
        if (repo.existsById(id)) {
            user.setId(id);
            return repo.save(user);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repo.existsByUsername(username);
    }

    @Override
    public User save(User newUser) {
        return null;
    }
}
