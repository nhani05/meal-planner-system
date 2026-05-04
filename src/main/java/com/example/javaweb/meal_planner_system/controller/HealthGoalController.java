package com.example.javaweb.meal_planner_system.controller;

import com.example.javaweb.meal_planner_system.dto.HealthGoalDTO;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.service.HealthGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health-goal")
@CrossOrigin(origins = "*")
public class HealthGoalController {

    @Autowired
    private HealthGoalService healthGoalService;

    @GetMapping("/{accountId}")
    public ResponseEntity<?> getHealthGoal(@PathVariable Long accountId) {
        return healthGoalService.findByAccountId(accountId)
                .map(goal -> ResponseEntity.ok(healthGoalService.convertToDTO(goal)))
                .orElseThrow(() -> new ResourceNotFoundException("Health goal not found for account " + accountId));
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<?> createOrUpdateHealthGoal(
            @PathVariable Long accountId,
            @RequestBody HealthGoalDTO healthGoalDTO) {
        return ResponseEntity.ok(healthGoalService.createOrUpdateForAccount(accountId, healthGoalDTO));
    }
}
