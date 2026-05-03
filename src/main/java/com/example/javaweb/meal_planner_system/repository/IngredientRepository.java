package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Ingredient entity
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByDishId(Long dishId);
}
