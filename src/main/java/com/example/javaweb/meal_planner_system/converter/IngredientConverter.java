package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.dto.IngredientDTO;
import com.example.javaweb.meal_planner_system.entity.Ingredient;

public class IngredientConverter {
    public static IngredientDTO toDTO(Ingredient entity) {
        if (entity == null) return null;
        IngredientDTO dto = new IngredientDTO();
        dto.setId(entity.getId());
        dto.setDishId(entity.getDish().getId());
        dto.setName(entity.getName());
        dto.setQuantityG(entity.getQuantityG());
        dto.setUnit(entity.getUnit());
        return dto;
    }
}
