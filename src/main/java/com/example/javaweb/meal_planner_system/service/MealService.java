package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.entity.Meal;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import java.util.List;

public interface MealService {
    Meal getOrCreateMeal(Long mealPlanId, MealType mealType);
    List<Meal> findByMealPlanId(Long mealPlanId);
}
