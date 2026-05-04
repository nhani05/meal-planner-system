package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.dto.MealDTO;
import com.example.javaweb.meal_planner_system.entity.Meal;

public class MealConverter {
    public static MealDTO toDTO(Meal entity) {
        if (entity == null) return null;
        MealDTO dto = new MealDTO();
        dto.setId(entity.getId());
        dto.setMealPlanId(entity.getMealPlan().getId());
        dto.setMealType(entity.getMealType());
        return dto;
    }
}
