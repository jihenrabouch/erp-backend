package com.erp.backend.service;

import java.util.Map;

public interface ReportService {
    Map<String, Object> projectStats(Long projectId);
}
