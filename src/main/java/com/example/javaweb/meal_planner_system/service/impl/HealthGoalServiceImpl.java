package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.HealthGoalConverter;
import com.example.javaweb.meal_planner_system.dto.HealthGoalDTO;
import com.example.javaweb.meal_planner_system.entity.HealthGoal;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.repository.HealthGoalRepository;
import com.example.javaweb.meal_planner_system.service.HealthGoalService;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HealthGoalServiceImpl implements HealthGoalService {

    @Autowired
    private HealthGoalRepository healthGoalRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public Optional<HealthGoal> findByAccountId(Long accountId) {
        return healthGoalRepository.findFirstByAccountIdOrderByUpdatedAtDesc(accountId);
    }

    @Override
    public HealthGoalDTO convertToDTO(HealthGoal healthGoal) {
        return HealthGoalConverter.toDTO(healthGoal);
    }

    @Override
    public HealthGoalDTO createOrUpdateForAccount(Long accountId, HealthGoalDTO healthGoalDTO) {
        UserAccount account = userAccountService.findById(accountId);
        
        HealthGoal goal = healthGoalRepository.findFirstByAccountIdOrderByUpdatedAtDesc(accountId)
                .orElse(new HealthGoal());
        
        goal.setAccount(account);
        goal.setGoalType(healthGoalDTO.getGoalType());
        goal.setActivityLevel(healthGoalDTO.getActivityLevel());
        goal.setTargetWeightKg(healthGoalDTO.getTargetWeightKg());
        goal.setDailyCaloriesKcal(healthGoalDTO.getDailyCaloriesKcal());
        goal.setProteinGDay(healthGoalDTO.getProteinGDay());
        goal.setCarbGDay(healthGoalDTO.getCarbGDay());
        goal.setFatGDay(healthGoalDTO.getFatGDay());
        
        HealthGoal saved = healthGoalRepository.save(goal);
        return convertToDTO(saved);
    }
}
