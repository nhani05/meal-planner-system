package com.example.javaweb.meal_planner_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for MealPlanTemplate
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlanTemplateDTO {
    private Long id;
    private String templateName;
    private LocalDateTime savedAt;
}
