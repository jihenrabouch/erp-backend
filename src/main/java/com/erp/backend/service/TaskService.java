package com.erp.backend.service;

import com.erp.backend.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAll();
    List<Task> getByProject(Long projectId);
    Optional<Task> getById(Long id);
    Task create(Task t);
    Task update(Long id, Task t);
    void delete(Long id);
}
