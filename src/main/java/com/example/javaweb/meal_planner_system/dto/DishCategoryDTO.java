package com.example.javaweb.meal_planner_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for DishCategory
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishCategoryDTO {
    private Integer id;
    private String name;
}
