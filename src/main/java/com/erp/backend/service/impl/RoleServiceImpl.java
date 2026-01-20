package com.erp.backend.service.impl;

import com.erp.backend.entity.ERole;
import com.erp.backend.entity.Role;
import com.erp.backend.repository.RoleRepository;
import com.erp.backend.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repo;
    public RoleServiceImpl(RoleRepository repo) { this.repo = repo; }

    @Override public List<Role> getAll() { return repo.findAll(); }
    @Override public Optional<Role> getById(Long id) { return repo.findById(id); }
    @Override public Role create(Role role) { return repo.save(role); }
    @Override public Optional<Role> findByName(ERole name) { return repo.findByName(name); } // ✅ ERole au lieu de String
}