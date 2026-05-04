package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.PortionDTO;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;

public interface PortionService {
    PortionDTO addPortion(Long mealPlanId, MealType mealType, PortionDTO portionDTO);
    PortionDTO updatePortion(Long portionId, PortionDTO portionDTO);
    void deletePortion(Long portionId);
}
