package com.example.javaweb.meal_planner_system.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    private List<MealDTO> meals;

    public MealPlanDTO(Long id, String planName, LocalDate planDate) {
        this.id = id;
        this.planName = planName;
        this.planDate = planDate;
    }
}
