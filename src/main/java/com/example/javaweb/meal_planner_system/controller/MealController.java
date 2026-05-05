package com.example.javaweb.meal_planner_system.controller;

import com.example.javaweb.meal_planner_system.converter.MealConverter;
import com.example.javaweb.meal_planner_system.dto.MealDTO;
import com.example.javaweb.meal_planner_system.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// Module: Controller
/**
 * Controller for meals within a meal plan
 */
@RestController
@RequestMapping("/meal-plans")
@CrossOrigin(origins = "*")
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/{planId}/meals")
    public ResponseEntity<?> getMealsByPlanId(@PathVariable Long planId) {
        List<MealDTO> meals = mealService.findByMealPlanId(planId).stream()
                .map(MealConverter::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(meals);
    }
}
