package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.PortionConverter;
import com.example.javaweb.meal_planner_system.dto.PortionDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.Meal;
import com.example.javaweb.meal_planner_system.entity.NutritionInfo;
import com.example.javaweb.meal_planner_system.entity.Portion;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.NutritionInfoRepository;
import com.example.javaweb.meal_planner_system.repository.PortionRepository;
import com.example.javaweb.meal_planner_system.service.DishService;
import com.example.javaweb.meal_planner_system.service.MealService;
import com.example.javaweb.meal_planner_system.service.PortionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PortionServiceImpl implements PortionService {

    @Autowired
    private PortionRepository portionRepository;

    @Autowired
    private MealService mealService;

    @Autowired
    private DishService dishService;

    @Autowired
    private NutritionInfoRepository nutritionInfoRepository;

    @Override
    public PortionDTO addPortion(Long mealPlanId, MealType mealType, PortionDTO dto) {
        Meal meal = mealService.getOrCreateMeal(mealPlanId, mealType);
        Dish dish = dishService.findById(dto.getDishId());
        
        Portion portion = new Portion();
        portion.setMeal(meal);
        portion.setDish(dish);
        portion.setQuantityG(dto.getQuantityG());
        
        calculateNutrition(portion);
        
        Portion saved = portionRepository.save(portion);
        return PortionConverter.toDTO(saved);
    }

    @Override
    public PortionDTO updatePortion(Long portionId, PortionDTO dto) {
        Portion portion = portionRepository.findById(portionId)
                .orElseThrow(() -> new ResourceNotFoundException("Portion not found"));
        
        portion.setQuantityG(dto.getQuantityG());
        calculateNutrition(portion);
        
        Portion saved = portionRepository.save(portion);
        return PortionConverter.toDTO(saved);
    }

    @Override
    public void deletePortion(Long portionId) {
        portionRepository.deleteById(portionId);
    }

    private void calculateNutrition(Portion portion) {
        NutritionInfo info = nutritionInfoRepository.findByDishId(portion.getDish().getId())
                .orElse(null);
        
        if (info != null && portion.getQuantityG() != null) {
            BigDecimal qty = portion.getQuantityG();
            BigDecimal hundred = new BigDecimal("100");
            
            portion.setCaloriesKcal(info.getCaloriesPer100g().multiply(qty).divide(hundred, 2, RoundingMode.HALF_UP));
            portion.setProteinG(info.getProteinPer100g().multiply(qty).divide(hundred, 2, RoundingMode.HALF_UP));
            portion.setCarbG(info.getCarbPer100g().multiply(qty).divide(hundred, 2, RoundingMode.HALF_UP));
            portion.setFatG(info.getFatPer100g().multiply(qty).divide(hundred, 2, RoundingMode.HALF_UP));
        } else {
            // Default to zero if no nutrition info found
            portion.setCaloriesKcal(BigDecimal.ZERO);
            portion.setProteinG(BigDecimal.ZERO);
            portion.setCarbG(BigDecimal.ZERO);
            portion.setFatG(BigDecimal.ZERO);
        }
    }
}
