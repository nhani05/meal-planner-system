package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.DishCategoryDTO;
import java.util.List;

public interface DishCategoryService {
    List<DishCategoryDTO> findAll();
}
