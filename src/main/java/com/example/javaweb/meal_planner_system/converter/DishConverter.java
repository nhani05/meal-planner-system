package com.example.javaweb.meal_planner_system.converter;

// Module: Converter
import com.example.javaweb.meal_planner_system.dto.DishDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;

public final class DishConverter {
    private DishConverter() {}

    public static DishDTO toDTO(Dish dish) {
        if (dish == null) return null;
        return new DishDTO(
            dish.getId(),
            dish.getName(),
            dish.getCategory() != null ? dish.getCategory().getId() : null,
            dish.getImageUrl(),
            dish.getSource(),
            dish.getDifficulty(),
            dish.getTotalTimeMin()
        );
    }
}
