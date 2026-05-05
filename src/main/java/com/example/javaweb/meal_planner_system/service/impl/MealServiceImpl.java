package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.entity.Meal;
import com.example.javaweb.meal_planner_system.entity.MealPlan;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import com.example.javaweb.meal_planner_system.repository.MealPlanRepository;
import com.example.javaweb.meal_planner_system.repository.MealRepository;
import com.example.javaweb.meal_planner_system.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Override
    public Meal getOrCreateMeal(Long mealPlanId, MealType mealType) {
        return mealRepository.findByMealPlanIdAndMealType(mealPlanId, mealType)
                .orElseGet(() -> {
                    MealPlan plan = mealPlanRepository.findById(mealPlanId)
                            .orElseThrow(() -> new com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException("MealPlan not found"));
                    Meal meal = new Meal();
                    meal.setMealPlan(plan);
                    meal.setMealType(mealType);
                    return mealRepository.save(meal);
                });
    }

    @Override
    public List<Meal> findByMealPlanId(Long mealPlanId) {
        return mealRepository.findByMealPlanId(mealPlanId);
    }
}
