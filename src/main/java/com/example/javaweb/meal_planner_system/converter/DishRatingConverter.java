package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.dto.DishRatingDTO;
import com.example.javaweb.meal_planner_system.entity.DishRating;

public class DishRatingConverter {
    public static DishRatingDTO toDTO(DishRating entity) {
        if (entity == null) return null;
        DishRatingDTO dto = new DishRatingDTO();
        dto.setId(entity.getId());
        dto.setAccountId(entity.getAccount().getId());
        dto.setDishId(entity.getDish().getId());
        dto.setScore(entity.getScore());
        dto.setComment(entity.getComment());
        return dto;
    }
}
