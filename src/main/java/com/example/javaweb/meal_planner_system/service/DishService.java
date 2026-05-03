package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.DishDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;

import java.util.List;

/**
 * Service interface for Dish
 */
public interface DishService {
    Dish save(Dish dish);
    Dish findById(Long id);
    List<Dish> findBySource(DishSource source);
    List<Dish> findByAccountId(Long accountId);
    List<Dish> findAll();
    DishDTO convertToDTO(Dish dish);
    void delete(Long id);
}
