package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.MealPlanDTO;
import com.example.javaweb.meal_planner_system.entity.MealPlan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for MealPlan
 */
public interface MealPlanService {
    MealPlan save(MealPlan mealPlan);
    MealPlan findById(Long id);
    List<MealPlan> findByAccountId(Long accountId);
    List<MealPlan> findByAccountIdAndDateBetween(Long accountId, LocalDate startDate, LocalDate endDate);
    Optional<MealPlan> findByAccountIdAndPlanDate(Long accountId, LocalDate planDate);
    MealPlanDTO convertToDTO(MealPlan mealPlan);
    void delete(Long id);
    // Create a meal plan for an account from DTO
    MealPlanDTO createForAccount(Long accountId, MealPlanDTO mealPlanDTO);
    // Update an existing meal plan from DTO
    MealPlanDTO updateFromDTO(Long id, MealPlanDTO mealPlanDTO);
}
