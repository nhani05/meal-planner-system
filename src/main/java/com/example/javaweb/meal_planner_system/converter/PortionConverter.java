package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.dto.PortionDTO;
import com.example.javaweb.meal_planner_system.entity.Portion;

public class PortionConverter {
    public static PortionDTO toDTO(Portion entity) {
        if (entity == null) return null;
        PortionDTO dto = new PortionDTO();
        dto.setId(entity.getId());
        dto.setMealId(entity.getMeal().getId());
        dto.setDishId(entity.getDish().getId());
        dto.setQuantityG(entity.getQuantityG());
        dto.setCaloriesKcal(entity.getCaloriesKcal());
        dto.setProteinG(entity.getProteinG());
        dto.setCarbG(entity.getCarbG());
        dto.setFatG(entity.getFatG());
        return dto;
    }
}
