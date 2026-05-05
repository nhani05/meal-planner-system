package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.dto.HealthGoalDTO;
import com.example.javaweb.meal_planner_system.entity.HealthGoal;

public class HealthGoalConverter {
    public static HealthGoalDTO toDTO(HealthGoal entity) {
        if (entity == null) return null;
        HealthGoalDTO dto = new HealthGoalDTO();
        dto.setId(entity.getId());
        dto.setGoalType(entity.getGoalType());
        dto.setActivityLevel(entity.getActivityLevel());
        dto.setTargetWeightKg(entity.getTargetWeightKg());
        dto.setDailyCaloriesKcal(entity.getDailyCaloriesKcal());
        dto.setProteinGDay(entity.getProteinGDay());
        dto.setCarbGDay(entity.getCarbGDay());
        dto.setFatGDay(entity.getFatGDay());
        return dto;
    }
}
