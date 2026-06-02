package com.example.javaweb.meal_planner_system.service.impl;

// Module: Service

import com.example.javaweb.meal_planner_system.dto.*;
import com.example.javaweb.meal_planner_system.entity.*;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import com.example.javaweb.meal_planner_system.repository.MealPlanRepository;
import com.example.javaweb.meal_planner_system.repository.MealRepository;
import com.example.javaweb.meal_planner_system.repository.PortionRepository;
import com.example.javaweb.meal_planner_system.repository.NutritionInfoRepository;
import com.example.javaweb.meal_planner_system.repository.DishRepository;
import com.example.javaweb.meal_planner_system.service.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of MealPlanService
 */
@Service
public class MealPlanServiceImpl implements MealPlanService {

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private com.example.javaweb.meal_planner_system.service.UserAccountService userAccountService;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private PortionRepository portionRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private NutritionInfoRepository nutritionInfoRepository;

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
    public MealPlanDTO convertToDetailedDTO(MealPlan mealPlan) {
        MealPlanDTO dto = convertToDTO(mealPlan);
        List<MealDTO> meals = mealRepository.findByMealPlanId(mealPlan.getId()).stream()
                .map(meal -> {
                    MealDTO mealDTO = new MealDTO(meal.getId(), mealPlan.getId(), meal.getMealType());
                    List<PortionDTO> portions = portionRepository.findByMealId(meal.getId()).stream()
                            .map(portion -> new PortionDTO(
                                    portion.getId(),
                                    meal.getId(),
                                    portion.getDish() != null ? portion.getDish().getId() : null,
                                    portion.getQuantityG(),
                                    portion.getCaloriesKcal(),
                                    portion.getProteinG(),
                                    portion.getCarbG(),
                                    portion.getFatG()
                            ))
                            .collect(Collectors.toList());
                    mealDTO.setPortions(portions);
                    return mealDTO;
                })
                .collect(Collectors.toList());
        dto.setMeals(meals);
        return dto;
    }

    @Override
    public void delete(Long id) {
        mealPlanRepository.deleteById(id);
    }

    @Override
    public MealPlanDTO createForAccount(Long accountId, MealPlanDTO mealPlanDTO) {
        com.example.javaweb.meal_planner_system.entity.UserAccount account = userAccountService.findById(accountId);

        MealPlan plan = new MealPlan();
        plan.setAccount(account);
        plan.setPlanName(mealPlanDTO.getPlanName());
        plan.setPlanDate(mealPlanDTO.getPlanDate());

        MealPlan saved = mealPlanRepository.save(plan);
        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public MealPlanDTO updateFromDTO(Long id, MealPlanUpdateDTO dto) {
        MealPlan plan = findById(id);

        if (dto.getPlanName() != null) {
            plan.setPlanName(dto.getPlanName());
        }
        if (dto.getPlanDate() != null) {
            plan.setPlanDate(dto.getPlanDate());
        }
        mealPlanRepository.save(plan);

        // Xóa portions và meals cũ
        List<Meal> existingMeals = mealRepository.findByMealPlanId(id);
        for (Meal meal : existingMeals) {
            List<Portion> portions = portionRepository.findByMealId(meal.getId());
            portionRepository.deleteAll(portions);
        }
        mealRepository.deleteAll(existingMeals);

        // Tạo meals và portions mới
        if (dto.getMeals() != null) {
            for (MealUpdateDTO mealDTO : dto.getMeals()) {
                Meal meal = new Meal();
                meal.setMealPlan(plan);
                meal.setMealType(mealDTO.getMealType() != null ? mealDTO.getMealType() : MealType.BREAKFAST);
                Meal savedMeal = mealRepository.save(meal);

                if (mealDTO.getPortions() != null) {
                    for (PortionUpdateDTO portionDTO : mealDTO.getPortions()) {
                        Dish dish = dishRepository.findById(portionDTO.getDishId())
                                .orElseThrow(() -> new com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException("Dish not found with id " + portionDTO.getDishId()));

                        Portion portion = new Portion();
                        portion.setMeal(savedMeal);
                        portion.setDish(dish);
                        portion.setQuantityG(portionDTO.getQuantityG());

                        // Auto-calculate nutrition from NutritionInfo
                        NutritionInfo nutrition = nutritionInfoRepository.findByDishId(dish.getId()).orElse(null);
                        if (nutrition != null && portionDTO.getQuantityG() != null) {
                            BigDecimal factor = portionDTO.getQuantityG().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
                            portion.setCaloriesKcal(multiplyNullable(nutrition.getCaloriesPer100g(), factor));
                            portion.setProteinG(multiplyNullable(nutrition.getProteinPer100g(), factor));
                            portion.setCarbG(multiplyNullable(nutrition.getCarbPer100g(), factor));
                            portion.setFatG(multiplyNullable(nutrition.getFatPer100g(), factor));
                        }

                        portionRepository.save(portion);
                    }
                }
            }
        }

        return convertToDetailedDTO(plan);
    }

    private BigDecimal multiplyNullable(BigDecimal value, BigDecimal factor) {
        if (value == null) return null;
        return value.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }
}
