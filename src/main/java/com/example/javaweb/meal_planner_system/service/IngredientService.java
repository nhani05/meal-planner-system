package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.IngredientDTO;
import java.util.List;

public interface IngredientService {
    IngredientDTO save(IngredientDTO ingredientDTO);
    IngredientDTO findById(Long id);
    List<IngredientDTO> findByDishId(Long dishId);
    void delete(Long id);
}
