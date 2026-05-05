package com.example.javaweb.meal_planner_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for admin create/update dish with nutrition and ingredients
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDishRequestDTO {
    private DishDTO dish;
    private NutritionInfoDTO nutrition;
    private List<IngredientDTO> ingredients;
}
