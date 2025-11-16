package com.erp.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private String status; // TODO, IN_PROGRESS, REVIEW, DONE
    private Double estimatedHours;
    private Double actualHours;

    @ManyToMany
    private List<User> assignees = new ArrayList<>();

    @ManyToMany
    private List<Label> labels = new ArrayList<>();

    @ManyToOne
    private Project project;
}
