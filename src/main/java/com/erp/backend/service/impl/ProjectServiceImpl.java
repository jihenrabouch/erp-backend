package com.erp.backend.service.impl;

import com.erp.backend.entity.Project;
import com.erp.backend.repository.ProjectRepository;
import com.erp.backend.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository repo;
    public ProjectServiceImpl(ProjectRepository repo) { this.repo = repo; }

    @Override
    public List<Project> getAll()
    {
        return repo.findAll();
    }
    @Override public Optional<Project> getById(Long id) { return repo.findById(id); }
    @Override public Project create(Project p) { return repo.save(p); }
    @Override public Project update(Long id, Project p) { p.setId(id); return repo.save(p); }
    @Override public void delete(Long id) { repo.deleteById(id); }
}
