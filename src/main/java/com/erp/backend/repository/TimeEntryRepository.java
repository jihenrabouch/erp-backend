package com.erp.backend.repository;

import com.erp.backend.entity.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    List<TimeEntry> findByTaskId(Long taskId);
    List<TimeEntry> findByUserId(Long userId);
}
