package com.erp.backend.controller;

import com.erp.backend.entity.Task;
import com.erp.backend.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        try {
            List<Task> tasks = service.getAll();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable Long projectId) {
        try {
            List<Task> tasks = service.getByProject(projectId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        try {
            return service.getById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        try {
            Task createdTask = service.create(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création de la tâche: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            // Vérifier si la tâche existe
            if (!service.getById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }

            // S'assurer que l'ID dans le path correspond à l'ID dans le body
            task.setId(id);
            Task updatedTask = service.update(id, task);
            return ResponseEntity.ok(updatedTask);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la mise à jour de la tâche: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            // Vérifier si la tâche existe avant de supprimer
            if (!service.getById(id).isPresent()) {
                return ResponseEntity.notFound().build();
            }

            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression de la tâche: " + e.getMessage());
        }
    }
}