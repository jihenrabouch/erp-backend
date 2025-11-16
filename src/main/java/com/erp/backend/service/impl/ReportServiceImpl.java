package com.erp.backend.service.impl;

import com.erp.backend.service.ReportService;
import com.erp.backend.repository.TaskRepository;
import com.erp.backend.repository.TimeEntryRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    private final TaskRepository taskRepo;
    private final TimeEntryRepository timeRepo;

    public ReportServiceImpl(TaskRepository taskRepo, TimeEntryRepository timeRepo) {
        this.taskRepo = taskRepo;
        this.timeRepo = timeRepo;
    }

    @Override
    public Map<String, Object> projectStats(Long projectId) {
        Map<String, Object> m = new HashMap<>();
        long tasks = taskRepo.findByProjectId(projectId).size();
        double totalHours = timeRepo.findByTaskId(projectId).stream()
                .mapToDouble(te -> te.getDurationHours() == null ? 0 : te.getDurationHours()).sum();
        m.put("tasks", tasks);
        m.put("totalHours", totalHours);
        return m;
    }
}
