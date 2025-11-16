package com.erp.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(length = 2000)
    private String description;
    private String category; // development, study, delivery, internal...
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    private User projectManager;

    @ManyToMany
    private List<User> members = new ArrayList<>();
}
