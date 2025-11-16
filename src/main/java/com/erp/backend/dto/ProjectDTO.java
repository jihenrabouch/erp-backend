package com.erp.backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
}
