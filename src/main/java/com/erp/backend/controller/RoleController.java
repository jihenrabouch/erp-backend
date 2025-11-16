package com.erp.backend.controller;

import com.erp.backend.entity.Role;
import com.erp.backend.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin
public class RoleController {
    private final RoleService service;
    public RoleController(RoleService service) { this.service = service; }

    @GetMapping
    public List<Role> all() { return service.getAll(); }

    @PostMapping
    public Role create(@RequestBody Role r) { return service.create(r); }

    @GetMapping("/{id}")
    public ResponseEntity<Role> get(@PathVariable Long id) { return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }
}
