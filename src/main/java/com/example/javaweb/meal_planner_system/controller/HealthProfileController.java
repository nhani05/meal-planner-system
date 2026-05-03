package com.example.javaweb.meal_planner_system.controller;

// Module: Controller

import com.example.javaweb.meal_planner_system.dto.HealthProfileDTO;
import com.example.javaweb.meal_planner_system.entity.HealthProfile;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.service.HealthProfileService;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for health profile management
 */
@RestController
@RequestMapping("/api/health-profile")
@CrossOrigin(origins = "*")
public class HealthProfileController {

    @Autowired
    private HealthProfileService healthProfileService;

    @Autowired
    private UserAccountService userAccountService;

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
        UserAccount account = userAccountService.findById(accountId); // will throw if not found

        HealthProfile profile = healthProfileService.findByAccountId(accountId).orElse(new HealthProfile());
        profile.setAccount(account);
        profile.setFullName(healthProfileDTO.getFullName());
        profile.setAge(healthProfileDTO.getAge());
        profile.setGender(healthProfileDTO.getGender());
        profile.setHeightCm(healthProfileDTO.getHeightCm());
        profile.setWeightKg(healthProfileDTO.getWeightKg());
        profile.setAvatarUrl(healthProfileDTO.getAvatarUrl());

        HealthProfile saved = healthProfileService.save(profile);
        return ResponseEntity.ok(healthProfileService.convertToDTO(saved));
    }
}
