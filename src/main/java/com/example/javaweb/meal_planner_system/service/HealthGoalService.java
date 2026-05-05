package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.HealthGoalDTO;
import com.example.javaweb.meal_planner_system.entity.HealthGoal;

import java.util.Optional;

public interface HealthGoalService {
    Optional<HealthGoal> findByAccountId(Long accountId);
    HealthGoalDTO convertToDTO(HealthGoal healthGoal);
    HealthGoalDTO createOrUpdateForAccount(Long accountId, HealthGoalDTO healthGoalDTO);
}
