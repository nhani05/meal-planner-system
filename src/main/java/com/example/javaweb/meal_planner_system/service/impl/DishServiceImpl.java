package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.DishConverter;
import com.example.javaweb.meal_planner_system.dto.AdminDishRequestDTO;
import com.example.javaweb.meal_planner_system.dto.DishDTO;
import com.example.javaweb.meal_planner_system.dto.IngredientDTO;
import com.example.javaweb.meal_planner_system.dto.NutritionInfoDTO;
import com.example.javaweb.meal_planner_system.entity.*;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import com.example.javaweb.meal_planner_system.exception.BadRequestException;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.*;
import com.example.javaweb.meal_planner_system.service.DishService;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementation of DishService with basic validation and exception handling.
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private PortionRepository portionRepository;

    @Autowired
    private NutritionInfoRepository nutritionInfoRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public Dish save(Dish dish) {
        if (dish == null) {
            throw new BadRequestException("Dish must not be null");
        }
        if (dish.getName() == null || dish.getName().trim().isEmpty()) {
            throw new BadRequestException("Dish name is required");
        }
        return dishRepository.save(dish);
    }

    @Override
    public Dish findById(Long id) {
        if (id == null) {
            throw new BadRequestException("Dish id must not be null");
        }
        return dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found with id " + id));
    }

    @Override
    public List<Dish> findBySource(DishSource source) {
        return dishRepository.findBySource(source);
    }

    @Override
    public List<Dish> findByAccountId(Long accountId) {
        return dishRepository.findByAccountId(accountId);
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public DishDTO convertToDTO(Dish dish) {
        NutritionInfo nutritionInfo = nutritionInfoRepository.findByDishId(dish.getId()).orElse(null);
        return DishConverter.toDTO(dish, nutritionInfo);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new BadRequestException("Dish id must not be null");
        }
        if (!dishRepository.existsById(id)) {
            throw new ResourceNotFoundException("Dish not found with id " + id);
        }
        List<com.example.javaweb.meal_planner_system.entity.Portion> portions = portionRepository.findByDishId(id);
        if (!portions.isEmpty()) {
            throw new BadRequestException("Cannot delete dish: it is currently used in meal plans");
        }
        dishRepository.deleteById(id);
    }

    @Override
    public Page<DishDTO> searchDishes(String keyword, Integer categoryId, String minCal, String maxCal, Pageable pageable) {
        BigDecimal minCalBd = (minCal != null && !minCal.isBlank()) ? new BigDecimal(minCal) : null;
        BigDecimal maxCalBd = (maxCal != null && !maxCal.isBlank()) ? new BigDecimal(maxCal) : null;
        return dishRepository.searchDishes(keyword, categoryId, minCalBd, maxCalBd, pageable)
                .map(dish -> {
                    // Fetch nutrition info for each dish
                    NutritionInfo nutritionInfo = nutritionInfoRepository.findByDishId(dish.getId()).orElse(null);
                    return DishConverter.toDTO(dish, nutritionInfo);
                });
    }

    @Override
    @Transactional
    public DishDTO createCustomDish(Long accountId, AdminDishRequestDTO request) {
        DishDTO dishDTO = request.getDish();
        if (dishDTO == null || dishDTO.getName() == null || dishDTO.getName().trim().isEmpty()) {
            throw new BadRequestException("Dish name is required");
        }

        Dish dish = new Dish();
        dish.setName(dishDTO.getName());
        dish.setImageUrl(dishDTO.getImageUrl());
        dish.setSource(DishSource.CUSTOM);
        dish.setAccount(userAccountService.findById(accountId));
        if (dishDTO.getCategoryId() != null) {
            dishCategoryRepository.findById(dishDTO.getCategoryId())
                    .ifPresent(dish::setCategory);
        }
        dish.setDifficulty(dishDTO.getDifficulty());
        dish.setTotalTimeMin(dishDTO.getTotalTimeMin());
        Dish savedDish = dishRepository.save(dish);

        NutritionInfoDTO nutDTO = request.getNutrition();
        if (nutDTO != null) {
            NutritionInfo nutrition = new NutritionInfo();
            nutrition.setDish(savedDish);
            nutrition.setCaloriesPer100g(nutDTO.getCaloriesPer100g());
            nutrition.setProteinPer100g(nutDTO.getProteinPer100g());
            nutrition.setCarbPer100g(nutDTO.getCarbPer100g());
            nutrition.setFatPer100g(nutDTO.getFatPer100g());
            nutrition.setFiberPer100g(nutDTO.getFiberPer100g());
            nutrition.setSatFatPer100g(nutDTO.getSatFatPer100g());
            nutrition.setVitaminAMcg(nutDTO.getVitaminAMcg());
            nutrition.setVitaminCMg(nutDTO.getVitaminCMg());
            nutrition.setVitaminDMcg(nutDTO.getVitaminDMcg());
            nutrition.setCalciumMg(nutDTO.getCalciumMg());
            nutrition.setIronMg(nutDTO.getIronMg());
            nutritionInfoRepository.save(nutrition);
        }

        List<IngredientDTO> ingredients = request.getIngredients();
        if (ingredients != null) {
            for (IngredientDTO ingDTO : ingredients) {
                Ingredient ingredient = new Ingredient();
                ingredient.setDish(savedDish);
                ingredient.setName(ingDTO.getName());
                ingredient.setQuantityG(ingDTO.getQuantityG());
                ingredient.setUnit(ingDTO.getUnit());
                ingredientRepository.save(ingredient);
            }
        }

        // Fetch and return nutrition info if it was saved
        NutritionInfo savedNutrition = nutritionInfoRepository.findByDishId(savedDish.getId()).orElse(null);
        return DishConverter.toDTO(savedDish, savedNutrition);
    }
}
