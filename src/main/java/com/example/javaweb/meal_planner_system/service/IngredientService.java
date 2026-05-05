package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.IngredientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IngredientService {
    IngredientDTO save(IngredientDTO ingredientDTO);
    IngredientDTO findById(Long id);
    List<IngredientDTO> findByDishId(Long dishId);
    void delete(Long id);
    Page<IngredientDTO> findAll(Pageable pageable);
    Page<IngredientDTO> searchByName(String name, Pageable pageable);
}
