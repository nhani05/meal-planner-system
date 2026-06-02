package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.dto.PortionDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.Meal;
import com.example.javaweb.meal_planner_system.entity.NutritionInfo;
import com.example.javaweb.meal_planner_system.entity.Portion;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.MealRepository;
import com.example.javaweb.meal_planner_system.repository.NutritionInfoRepository;
import com.example.javaweb.meal_planner_system.repository.PortionRepository;
import com.example.javaweb.meal_planner_system.service.DishService;
import com.example.javaweb.meal_planner_system.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PortionServiceImplTest {

    private PortionRepository portionRepository;
    private MealService mealService;
    private MealRepository mealRepository;
    private DishService dishService;
    private NutritionInfoRepository nutritionInfoRepository;
    private PortionServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        portionRepository = mock(PortionRepository.class);
        mealService = mock(MealService.class);
        mealRepository = mock(MealRepository.class);
        dishService = mock(DishService.class);
        nutritionInfoRepository = mock(NutritionInfoRepository.class);
        service = new PortionServiceImpl();
        setField(service, "portionRepository", portionRepository);
        setField(service, "mealService", mealService);
        setField(service, "mealRepository", mealRepository);
        setField(service, "dishService", dishService);
        setField(service, "nutritionInfoRepository", nutritionInfoRepository);
    }

    @Test
    void addPortionCreatesMealPortionAndCalculatesNutrition() {
        Meal meal = meal(10L);
        Dish dish = dish(20L);
        NutritionInfo info = nutritionInfo("100.00", "10.00", "20.00", "5.00");
        PortionDTO input = new PortionDTO();
        input.setDishId(20L);
        input.setQuantityG(new BigDecimal("250.00"));

        when(mealService.getOrCreateMeal(1L, MealType.DINNER)).thenReturn(meal);
        when(dishService.findById(20L)).thenReturn(dish);
        when(nutritionInfoRepository.findByDishId(20L)).thenReturn(Optional.of(info));
        when(portionRepository.save(any(Portion.class))).thenAnswer(invocation -> {
            Portion saved = invocation.getArgument(0);
            saved.setId(99L);
            return saved;
        });

        PortionDTO result = service.addPortion(1L, MealType.DINNER, input);

        assertEquals(99L, result.getId());
        assertEquals(10L, result.getMealId());
        assertEquals(20L, result.getDishId());
        assertEquals(new BigDecimal("250.00"), result.getQuantityG());
        assertEquals(new BigDecimal("250.00"), result.getCaloriesKcal());
        assertEquals(new BigDecimal("25.00"), result.getProteinG());
        assertEquals(new BigDecimal("50.00"), result.getCarbG());
        assertEquals(new BigDecimal("12.50"), result.getFatG());
    }

    @Test
    void updatePortionRecalculatesNutritionOrThrowsWhenMissing() {
        Portion portion = new Portion();
        portion.setId(8L);
        portion.setMeal(meal(10L));
        portion.setDish(dish(20L));
        when(portionRepository.findById(8L)).thenReturn(Optional.of(portion));
        when(nutritionInfoRepository.findByDishId(20L)).thenReturn(Optional.of(nutritionInfo("200.00", "4.00", "6.00", "2.00")));
        when(portionRepository.save(portion)).thenReturn(portion);

        PortionDTO input = new PortionDTO();
        input.setQuantityG(new BigDecimal("50.00"));

        PortionDTO result = service.updatePortion(8L, input);

        assertEquals(new BigDecimal("100.00"), result.getCaloriesKcal());
        assertEquals(new BigDecimal("2.00"), result.getProteinG());
        assertEquals(new BigDecimal("3.00"), result.getCarbG());
        assertEquals(new BigDecimal("1.00"), result.getFatG());

        when(portionRepository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updatePortion(9L, input));
    }

    @Test
    void addPortionDefaultsNutritionToZeroWhenNutritionInfoMissing() {
        Meal meal = meal(10L);
        Dish dish = dish(20L);
        PortionDTO input = new PortionDTO();
        input.setDishId(20L);
        input.setQuantityG(new BigDecimal("250.00"));

        when(mealService.getOrCreateMeal(1L, MealType.LUNCH)).thenReturn(meal);
        when(dishService.findById(20L)).thenReturn(dish);
        when(nutritionInfoRepository.findByDishId(20L)).thenReturn(Optional.empty());
        when(portionRepository.save(any(Portion.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PortionDTO result = service.addPortion(1L, MealType.LUNCH, input);

        assertEquals(BigDecimal.ZERO, result.getCaloriesKcal());
        assertEquals(BigDecimal.ZERO, result.getProteinG());
        assertEquals(BigDecimal.ZERO, result.getCarbG());
        assertEquals(BigDecimal.ZERO, result.getFatG());
    }

    @Test
    void getPortionsByMealPlanAndTypeReturnsEmptyWhenMealMissing() {
        when(mealRepository.findByMealPlanIdAndMealType(1L, MealType.BREAKFAST)).thenReturn(Optional.empty());

        List<PortionDTO> result = service.getPortionsByMealPlanAndType(1L, MealType.BREAKFAST);

        assertEquals(0, result.size());
    }

    @Test
    void getPortionsByMealPlanAndTypeMapsFoundPortions() {
        Meal meal = meal(5L);
        Portion portion = new Portion();
        portion.setId(6L);
        portion.setMeal(meal);
        portion.setDish(dish(7L));
        portion.setQuantityG(new BigDecimal("100.00"));

        when(mealRepository.findByMealPlanIdAndMealType(1L, MealType.BREAKFAST)).thenReturn(Optional.of(meal));
        when(portionRepository.findByMealId(5L)).thenReturn(List.of(portion));

        List<PortionDTO> result = service.getPortionsByMealPlanAndType(1L, MealType.BREAKFAST);

        assertEquals(1, result.size());
        assertEquals(6L, result.get(0).getId());
        assertEquals(5L, result.get(0).getMealId());
        assertEquals(7L, result.get(0).getDishId());
    }

    @Test
    void deletePortionDelegatesToRepository() {
        service.deletePortion(4L);

        verify(portionRepository).deleteById(4L);
    }

    private static Meal meal(Long id) {
        Meal meal = new Meal();
        meal.setId(id);
        return meal;
    }

    private static Dish dish(Long id) {
        Dish dish = new Dish();
        dish.setId(id);
        return dish;
    }

    private static NutritionInfo nutritionInfo(String calories, String protein, String carb, String fat) {
        NutritionInfo info = new NutritionInfo();
        info.setCaloriesPer100g(new BigDecimal(calories));
        info.setProteinPer100g(new BigDecimal(protein));
        info.setCarbPer100g(new BigDecimal(carb));
        info.setFatPer100g(new BigDecimal(fat));
        return info;
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
