package com.example.javaweb.meal_planner_system.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * DTO for Admin Audit Log
 */
@Data
public class AdminAuditLogDTO {
    private Long id;
    private Long adminId;
    private String adminUsername;
    private String action;
    private String targetEntity;
    private Long targetId;
    private String details;
    private LocalDateTime performedAt;
}
