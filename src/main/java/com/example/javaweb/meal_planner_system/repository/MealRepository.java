package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.Meal;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Meal entity
 */
@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByMealPlanId(Long mealPlanId);
    Optional<Meal> findByMealPlanIdAndMealType(Long mealPlanId, MealType mealType);
}
