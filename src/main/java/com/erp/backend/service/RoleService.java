package com.erp.backend.service;

import com.erp.backend.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getAll();
    Optional<Role> getById(Long id);
    Role create(Role role);
    Optional<Role> findByName(String name);
}
