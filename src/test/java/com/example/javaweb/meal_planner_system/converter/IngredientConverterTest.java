package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.dto.IngredientDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.Ingredient;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IngredientConverterTest {

    @Test
    void toDTO_whenIngredientIsNull_returnsNull() {
        assertNull(IngredientConverter.toDTO(null));
    }

    @Test
    void toDTO_mapsIngredientFieldsAndDishId() {
        Dish dish = new Dish();
        dish.setId(20L);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(7L);
        ingredient.setDish(dish);
        ingredient.setName("Brown rice");
        ingredient.setQuantityG(new BigDecimal("150.00"));
        ingredient.setUnit("g");

        IngredientDTO dto = IngredientConverter.toDTO(ingredient);

        assertEquals(7L, dto.getId());
        assertEquals(20L, dto.getDishId());
        assertEquals("Brown rice", dto.getName());
        assertEquals(new BigDecimal("150.00"), dto.getQuantityG());
        assertEquals("g", dto.getUnit());
    }
}
