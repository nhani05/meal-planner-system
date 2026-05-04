package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.DishRatingDTO;
import java.util.List;

public interface DishRatingService {
    DishRatingDTO rate(Long dishId, Long accountId, DishRatingDTO ratingDTO);
    List<DishRatingDTO> findByDishId(Long dishId);
}
