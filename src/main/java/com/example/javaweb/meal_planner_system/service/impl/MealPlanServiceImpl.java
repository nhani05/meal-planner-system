package com.example.javaweb.meal_planner_system.service.impl;

// Module: Service

import com.example.javaweb.meal_planner_system.dto.MealPlanDTO;
import com.example.javaweb.meal_planner_system.entity.MealPlan;
import com.example.javaweb.meal_planner_system.repository.MealPlanRepository;
import com.example.javaweb.meal_planner_system.service.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of MealPlanService
 */
@Service
public class MealPlanServiceImpl implements MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Override
    public MealPlan save(MealPlan mealPlan) {
        return mealPlanRepository.save(mealPlan);
    }

    @Override
    public MealPlan findById(Long id) {
        if (id == null) {
            throw new com.example.javaweb.meal_planner_system.exception.BadRequestException("MealPlan id must not be null");
        }
        return mealPlanRepository.findById(id)
                .orElseThrow(() -> new com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException("MealPlan not found with id " + id));
    }

    @Override
    public List<MealPlan> findByAccountId(Long accountId) {
        return mealPlanRepository.findByAccountId(accountId);
    }

    @Override
    public List<MealPlan> findByAccountIdAndDateBetween(Long accountId, LocalDate startDate, LocalDate endDate) {
        return mealPlanRepository.findByAccountIdAndPlanDateBetween(accountId, startDate, endDate);
    }

    @Override
    public Optional<MealPlan> findByAccountIdAndPlanDate(Long accountId, LocalDate planDate) {
        return mealPlanRepository.findByAccountIdAndPlanDate(accountId, planDate);
    }

    @Override
    public MealPlanDTO convertToDTO(MealPlan mealPlan) {
        return com.example.javaweb.meal_planner_system.converter.MealPlanConverter.toDTO(mealPlan);
    }

    @Override
    public void delete(Long id) {
        mealPlanRepository.deleteById(id);
    }
}
