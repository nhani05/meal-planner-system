package com.example.javaweb.meal_planner_system.dto;

import com.example.javaweb.meal_planner_system.entity.enums.DishDifficulty;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Dish
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDTO {
    private Long id;
    private String name;
    private Integer categoryId;
    private String imageUrl;
    private DishSource source;
    private DishDifficulty difficulty;
    private Integer totalTimeMin;
}
