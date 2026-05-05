package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.dto.DishCategoryDTO;
import com.example.javaweb.meal_planner_system.entity.DishCategory;

public class DishCategoryConverter {
    public static DishCategoryDTO toDTO(DishCategory entity) {
        if (entity == null) return null;
        DishCategoryDTO dto = new DishCategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
}
