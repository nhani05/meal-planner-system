package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.DishDTO;
import java.util.List;

public interface FavoriteDishService {
    void addFavorite(Long accountId, Long dishId);
    void removeFavorite(Long accountId, Long dishId);
    List<DishDTO> findByAccountId(Long accountId);
}
