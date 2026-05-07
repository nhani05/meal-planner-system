package com.example.javaweb.meal_planner_system.dto;

import com.example.javaweb.meal_planner_system.entity.enums.DishDifficulty;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    /**
     * Simplified nutrition info for frontend display (per serving, approx 300g)
     */
    private NutritionSummaryDTO nutritionInfo;

    /**
     * Inner DTO for simplified nutrition display
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NutritionSummaryDTO {
        private BigDecimal caloriesKcal;
        private BigDecimal proteinG;
        private BigDecimal carbG;
        private BigDecimal fatG;
    }
}
