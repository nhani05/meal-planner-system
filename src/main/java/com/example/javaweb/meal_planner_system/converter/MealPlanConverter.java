package com.example.javaweb.meal_planner_system.converter;

// Module: Converter
import com.example.javaweb.meal_planner_system.dto.MealPlanDTO;
import com.example.javaweb.meal_planner_system.entity.MealPlan;

public final class MealPlanConverter {
    private MealPlanConverter() {}

    public static MealPlanDTO toDTO(MealPlan plan) {
        if (plan == null) return null;
        return new MealPlanDTO(
            plan.getId(),
            plan.getPlanName(),
            plan.getPlanDate()
        );
    }
}
