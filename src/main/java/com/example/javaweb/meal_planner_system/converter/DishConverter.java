package com.example.javaweb.meal_planner_system.converter;

// Module: Converter
import com.example.javaweb.meal_planner_system.dto.DishDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.NutritionInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class DishConverter {
    private DishConverter() {}

    // Default serving size in grams for calculating per-serving nutrition
    private static final BigDecimal DEFAULT_SERVING_G = new BigDecimal("300");

    public static DishDTO toDTO(Dish dish) {
        if (dish == null) return null;
        return toDTO(dish, null);
    }

    public static DishDTO toDTO(Dish dish, NutritionInfo nutritionInfo) {
        if (dish == null) return null;

        DishDTO dto = new DishDTO();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setCategoryId(dish.getCategory() != null ? dish.getCategory().getId() : null);
        dto.setImageUrl(dish.getImageUrl());
        dto.setSource(dish.getSource());
        dto.setDifficulty(dish.getDifficulty());
        dto.setTotalTimeMin(dish.getTotalTimeMin());

        // Add nutrition info if available
        if (nutritionInfo != null) {
            dto.setNutritionInfo(calculateNutritionSummary(nutritionInfo));
        }

        return dto;
    }

    /**
     * Calculate nutrition per serving from per 100g values
     */
    private static DishDTO.NutritionSummaryDTO calculateNutritionSummary(NutritionInfo nutrition) {
        if (nutrition == null) return null;

        BigDecimal multiplier = DEFAULT_SERVING_G.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        DishDTO.NutritionSummaryDTO summary = new DishDTO.NutritionSummaryDTO();
        summary.setCaloriesKcal(multiply(nutrition.getCaloriesPer100g(), multiplier));
        summary.setProteinG(multiply(nutrition.getProteinPer100g(), multiplier));
        summary.setCarbG(multiply(nutrition.getCarbPer100g(), multiplier));
        summary.setFatG(multiply(nutrition.getFatPer100g(), multiplier));

        return summary;
    }

    private static BigDecimal multiply(BigDecimal value, BigDecimal multiplier) {
        if (value == null) return BigDecimal.ZERO;
        return value.multiply(multiplier).setScale(1, RoundingMode.HALF_UP);
    }
}
