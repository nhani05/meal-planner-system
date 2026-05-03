package com.example.javaweb.meal_planner_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for NutritionInfo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutritionInfoDTO {
    private Long id;
    private Long dishId;
    private BigDecimal caloriesPer100g;
    private BigDecimal proteinPer100g;
    private BigDecimal carbPer100g;
    private BigDecimal fatPer100g;
    private BigDecimal fiberPer100g;
    private BigDecimal satFatPer100g;
    private BigDecimal vitaminAMcg;
    private BigDecimal vitaminCMg;
    private BigDecimal vitaminDMcg;
    private BigDecimal calciumMg;
    private BigDecimal ironMg;
}
