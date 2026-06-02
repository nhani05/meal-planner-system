package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.dto.DishDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.DishCategory;
import com.example.javaweb.meal_planner_system.entity.NutritionInfo;
import com.example.javaweb.meal_planner_system.entity.enums.DishDifficulty;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DishConverterTest {

    @Test
    void toDTO_whenDishIsNull_returnsNull() {
        assertNull(DishConverter.toDTO(null));
    }

    @Test
    void toDTO_mapsDishFieldsAndCategoryId() {
        Dish dish = new Dish();
        dish.setId(10L);
        dish.setName("Chicken Rice");
        dish.setImageUrl("https://example.com/chicken-rice.jpg");
        dish.setSource(DishSource.SYSTEM);
        dish.setDifficulty(DishDifficulty.EASY);
        dish.setTotalTimeMin(35);

        DishCategory category = new DishCategory();
        category.setId(3);
        dish.setCategory(category);

        DishDTO dto = DishConverter.toDTO(dish);

        assertEquals(10L, dto.getId());
        assertEquals("Chicken Rice", dto.getName());
        assertEquals(3, dto.getCategoryId());
        assertEquals("https://example.com/chicken-rice.jpg", dto.getImageUrl());
        assertEquals(DishSource.SYSTEM, dto.getSource());
        assertEquals(DishDifficulty.EASY, dto.getDifficulty());
        assertEquals(35, dto.getTotalTimeMin());
        assertNull(dto.getNutritionInfo());
    }

    @Test
    void toDTO_whenNutritionProvided_calculatesPerServingSummaryFromPer100gValues() {
        Dish dish = new Dish();
        dish.setId(11L);
        dish.setName("Tofu Bowl");

        NutritionInfo nutrition = new NutritionInfo();
        nutrition.setCaloriesPer100g(new BigDecimal("123.45"));
        nutrition.setProteinPer100g(new BigDecimal("8.25"));
        nutrition.setCarbPer100g(new BigDecimal("10.10"));
        nutrition.setFatPer100g(new BigDecimal("4.44"));

        DishDTO dto = DishConverter.toDTO(dish, nutrition);

        assertEquals(new BigDecimal("370.4"), dto.getNutritionInfo().getCaloriesKcal());
        assertEquals(new BigDecimal("24.8"), dto.getNutritionInfo().getProteinG());
        assertEquals(new BigDecimal("30.3"), dto.getNutritionInfo().getCarbG());
        assertEquals(new BigDecimal("13.3"), dto.getNutritionInfo().getFatG());
    }

    @Test
    void toDTO_whenNutritionValuesAreNull_usesZeroInSummary() {
        Dish dish = new Dish();
        dish.setId(12L);
        dish.setName("Plain Soup");

        NutritionInfo nutrition = new NutritionInfo();

        DishDTO dto = DishConverter.toDTO(dish, nutrition);

        assertEquals(BigDecimal.ZERO, dto.getNutritionInfo().getCaloriesKcal());
        assertEquals(BigDecimal.ZERO, dto.getNutritionInfo().getProteinG());
        assertEquals(BigDecimal.ZERO, dto.getNutritionInfo().getCarbG());
        assertEquals(BigDecimal.ZERO, dto.getNutritionInfo().getFatG());
    }
}
