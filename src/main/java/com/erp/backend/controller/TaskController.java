package com.erp.backend.controller;

import com.erp.backend.entity.Task;
import com.erp.backend.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {
    private final TaskService service;
    public TaskController(TaskService service) { this.service = service; }

    @GetMapping
    public List<Task> all() { return service.getAll(); }

    @GetMapping("/project/{projectId}")
    public List<Task> byProject(@PathVariable Long projectId) { return service.getByProject(projectId); }

    @GetMapping("/{id}")
    public ResponseEntity<Task> get(@PathVariable Long id) { return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()); }

    @PostMapping
    public Task create(@RequestBody Task t) { return service.create(t); }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task t) {
        return service.getById(id).map(existing -> ResponseEntity.ok(service.update(id, t))).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok().build(); }
}
