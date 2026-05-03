package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.HealthProfileDTO;
import com.example.javaweb.meal_planner_system.entity.HealthProfile;

import java.util.Optional;

/**
 * Service interface for HealthProfile
 */
public interface HealthProfileService {
    HealthProfile save(HealthProfile healthProfile);
    Optional<HealthProfile> findByAccountId(Long accountId);
    HealthProfileDTO convertToDTO(HealthProfile healthProfile);
    HealthProfile findById(Long id);
}
