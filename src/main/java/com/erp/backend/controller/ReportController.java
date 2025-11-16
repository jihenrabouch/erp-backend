package com.erp.backend.controller;

import com.erp.backend.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin
public class ReportController {
    private final ReportService service;
    public ReportController(ReportService service) { this.service = service; }

    @GetMapping("/project/{projectId}")
    public Map<String,Object> projectStats(@PathVariable Long projectId) {
        return service.projectStats(projectId);
    }
}
