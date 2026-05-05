package com.example.javaweb.meal_planner_system.controller;

import com.example.javaweb.meal_planner_system.service.MealPlanTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Module: Controller
/**
 * Controller for meal plan template management
 */
@RestController
@RequestMapping("/meal-plan-templates")
@CrossOrigin(origins = "*")
public class MealPlanTemplateController {

    @Autowired
    private MealPlanTemplateService mealPlanTemplateService;

    @GetMapping
    public ResponseEntity<?> getTemplates(@RequestParam Long accountId) {
        return ResponseEntity.ok(mealPlanTemplateService.findByAccountId(accountId));
    }
}
