package com.example.javaweb.meal_planner_system.controller;

// Module: Controller

import com.example.javaweb.meal_planner_system.dto.HealthProfileDTO;
import com.example.javaweb.meal_planner_system.service.HealthProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for health profile management
 */
@RestController
@RequestMapping("/health-profile")
@CrossOrigin(origins = "*")
public class HealthProfileController {

    @Autowired
    private HealthProfileService healthProfileService;


    @GetMapping("/{accountId}")
    public ResponseEntity<?> getHealthProfile(@PathVariable Long accountId) {
        var profile = healthProfileService.findByAccountId(accountId)
                .orElseThrow(() -> new com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException("Health profile not found for account " + accountId));
        return ResponseEntity.ok(healthProfileService.convertToDTO(profile));
    }

    @PostMapping("/{accountId}")
    public ResponseEntity<?> createOrUpdateHealthProfile(
            @PathVariable Long accountId,
            @RequestBody HealthProfileDTO healthProfileDTO) {
        var savedDto = healthProfileService.createOrUpdateForAccount(accountId, healthProfileDTO);
        return ResponseEntity.ok(savedDto);
    }
}
