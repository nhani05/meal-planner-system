package com.example.javaweb.meal_planner_system.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for MealPlan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlanDTO {
    private Long id;
    private String planName;
    private LocalDate planDate;
}
