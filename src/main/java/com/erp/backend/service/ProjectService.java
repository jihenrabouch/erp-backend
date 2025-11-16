package com.erp.backend.service;

import com.erp.backend.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getAll();
    Optional<Project> getById(Long id);
    Project create(Project p);
    Project update(Long id, Project p);
    void delete(Long id);
}
