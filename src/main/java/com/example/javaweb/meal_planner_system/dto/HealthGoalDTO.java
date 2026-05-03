package com.example.javaweb.meal_planner_system.dto;

import com.example.javaweb.meal_planner_system.entity.enums.ActivityLevel;
import com.example.javaweb.meal_planner_system.entity.enums.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for HealthGoal
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthGoalDTO {
    private Long id;
    private GoalType goalType;
    private ActivityLevel activityLevel;
    private BigDecimal targetWeightKg;
    private Integer dailyCaloriesKcal;
    private BigDecimal proteinGDay;
    private BigDecimal carbGDay;
    private BigDecimal fatGDay;
}
