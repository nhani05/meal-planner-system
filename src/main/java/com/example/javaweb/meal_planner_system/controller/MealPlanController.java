package com.example.javaweb.meal_planner_system.controller;

// Module: Controller

import com.example.javaweb.meal_planner_system.dto.MealPlanDTO;
import com.example.javaweb.meal_planner_system.entity.MealPlan;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.service.MealPlanService;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for meal plan management
 */
@RestController
@RequestMapping("/meal-plans")
@CrossOrigin(origins = "*")
public class MealPlanController {

    @Autowired
    private MealPlanService mealPlanService;

    @Autowired
    private UserAccountService userAccountService;

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

        UserAccount account = userAccountService.findById(accountId); // will throw if not found

        MealPlan plan = new MealPlan();
        plan.setAccount(account);
        plan.setPlanName(mealPlanDTO.getPlanName());
        plan.setPlanDate(mealPlanDTO.getPlanDate());

        MealPlan saved = mealPlanService.save(plan);
        return ResponseEntity.ok(mealPlanService.convertToDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMealPlan(
            @PathVariable Long id,
            @RequestBody MealPlanDTO mealPlanDTO) {
        MealPlan plan = mealPlanService.findById(id); // will throw if not found

        plan.setPlanName(mealPlanDTO.getPlanName());
        plan.setPlanDate(mealPlanDTO.getPlanDate());

        MealPlan updated = mealPlanService.save(plan);
        return ResponseEntity.ok(mealPlanService.convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMealPlan(@PathVariable Long id) {
        mealPlanService.findById(id); // will throw if not found
        mealPlanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
