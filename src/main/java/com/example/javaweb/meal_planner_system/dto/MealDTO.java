package com.example.javaweb.meal_planner_system.dto;

import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Meal
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealDTO {
    private Long id;
    private Long mealPlanId;
    private MealType mealType;
    private List<PortionDTO> portions;

    public MealDTO(Long id, Long mealPlanId, MealType mealType) {
        this.id = id;
        this.mealPlanId = mealPlanId;
        this.mealType = mealType;
    }
}
