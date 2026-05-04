package com.example.javaweb.meal_planner_system.dto;

import com.example.javaweb.meal_planner_system.entity.enums.FeedbackStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackDTO {
    private Long id;
    private Long accountId;
    private String username;
    private String content;
    private FeedbackStatus status;
    private LocalDateTime submittedAt;
}
