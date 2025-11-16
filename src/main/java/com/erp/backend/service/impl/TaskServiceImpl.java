package com.erp.backend.service.impl;

import com.erp.backend.entity.Task;
import com.erp.backend.repository.TaskRepository;
import com.erp.backend.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repo;
    public TaskServiceImpl(TaskRepository repo) { this.repo = repo; }

    @Override public List<Task> getAll() { return repo.findAll(); }
    @Override public List<Task> getByProject(Long projectId) { return repo.findByProjectId(projectId); }
    @Override public Optional<Task> getById(Long id) { return repo.findById(id); }
    @Override public Task create(Task t) { return repo.save(t); }
    @Override public Task update(Long id, Task t) { t.setId(id); return repo.save(t); }
    @Override public void delete(Long id) { repo.deleteById(id); }
}
