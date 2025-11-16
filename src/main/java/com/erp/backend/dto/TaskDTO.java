package com.erp.backend.dto;

import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Double estimatedHours;
    private Double actualHours;
    private Long projectId;
}
