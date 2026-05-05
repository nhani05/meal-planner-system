package com.example.javaweb.meal_planner_system.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Module: Controller

import com.example.javaweb.meal_planner_system.dto.MealPlanDTO;
import com.example.javaweb.meal_planner_system.entity.MealPlan;
import com.example.javaweb.meal_planner_system.service.MealPlanService;

/**
 * Controller for meal plan management
 */
@RestController
@RequestMapping("/meal-plans")
@CrossOrigin(origins = "*")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMealPlanById(@PathVariable Long id) {
        MealPlan plan = mealPlanService.findById(id);
        return ResponseEntity.ok(mealPlanService.convertToDTO(plan));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<?> getMealPlans(@PathVariable Long accountId) {
        List<MealPlan> plans = mealPlanService.findByAccountId(accountId);
        return ResponseEntity.ok(plans.stream()
                .map(mealPlanService::convertToDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/account/{accountId}/date/{planDate}")
    public ResponseEntity<?> getMealPlanByDate(
            @PathVariable Long accountId,
            @PathVariable String planDate) {
        var plan = mealPlanService.findByAccountIdAndPlanDate(accountId, LocalDate.parse(planDate));
        var p = plan.orElseThrow(() -> new com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException("MealPlan not found for account " + accountId + " on " + planDate));
        return ResponseEntity.ok(mealPlanService.convertToDTO(p));
    }

    @PostMapping
    public ResponseEntity<?> createMealPlan(@RequestBody MealPlanDTO mealPlanDTO,
                                           @RequestParam Long accountId) {
        var savedDto = mealPlanService.createForAccount(accountId, mealPlanDTO);
        return ResponseEntity.ok(savedDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMealPlan(
            @PathVariable Long id,
            @RequestBody MealPlanDTO mealPlanDTO) {
        var updatedDto = mealPlanService.updateFromDTO(id, mealPlanDTO);
        return ResponseEntity.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMealPlan(@PathVariable Long id) {
        mealPlanService.findById(id); // will throw if not found
        mealPlanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
