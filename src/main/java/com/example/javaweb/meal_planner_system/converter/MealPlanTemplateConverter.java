package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.dto.MealPlanTemplateDTO;
import com.example.javaweb.meal_planner_system.entity.MealPlanTemplate;

public class MealPlanTemplateConverter {
    public static MealPlanTemplateDTO toDTO(MealPlanTemplate entity) {
        if (entity == null) return null;
        MealPlanTemplateDTO dto = new MealPlanTemplateDTO();
        dto.setId(entity.getId());
        dto.setTemplateName(entity.getTemplateName());
        dto.setSavedAt(entity.getSavedAt());
        return dto;
    }
}
