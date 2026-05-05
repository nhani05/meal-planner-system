package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.MealPlanTemplateConverter;
import com.example.javaweb.meal_planner_system.dto.MealPlanTemplateDTO;
import com.example.javaweb.meal_planner_system.repository.MealPlanTemplateRepository;
import com.example.javaweb.meal_planner_system.service.MealPlanTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealPlanTemplateServiceImpl implements MealPlanTemplateService {

    @Autowired
    private MealPlanTemplateRepository mealPlanTemplateRepository;

    @Override
    public List<MealPlanTemplateDTO> findByAccountId(Long accountId) {
        return mealPlanTemplateRepository.findByAccountId(accountId).stream()
                .map(MealPlanTemplateConverter::toDTO)
                .collect(Collectors.toList());
    }
}
