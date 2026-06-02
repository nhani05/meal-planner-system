package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.entity.Meal;
import com.example.javaweb.meal_planner_system.entity.MealPlan;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.MealPlanRepository;
import com.example.javaweb.meal_planner_system.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MealServiceImplTest {

    private MealRepository mealRepository;
    private MealPlanRepository mealPlanRepository;
    private MealServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        mealRepository = mock(MealRepository.class);
        mealPlanRepository = mock(MealPlanRepository.class);
        service = new MealServiceImpl();
        setField(service, "mealRepository", mealRepository);
        setField(service, "mealPlanRepository", mealPlanRepository);
    }

    @Test
    void getOrCreateMealReturnsExistingMeal() {
        Meal existing = new Meal();
        existing.setId(1L);
        when(mealRepository.findByMealPlanIdAndMealType(10L, MealType.BREAKFAST)).thenReturn(Optional.of(existing));

        Meal result = service.getOrCreateMeal(10L, MealType.BREAKFAST);

        assertSame(existing, result);
    }

    @Test
    void getOrCreateMealCreatesMealWhenMissing() {
        MealPlan plan = new MealPlan();
        plan.setId(10L);
        when(mealRepository.findByMealPlanIdAndMealType(10L, MealType.DINNER)).thenReturn(Optional.empty());
        when(mealPlanRepository.findById(10L)).thenReturn(Optional.of(plan));
        when(mealRepository.save(any(Meal.class))).thenAnswer(invocation -> {
            Meal meal = invocation.getArgument(0);
            meal.setId(5L);
            return meal;
        });

        Meal result = service.getOrCreateMeal(10L, MealType.DINNER);

        assertEquals(5L, result.getId());
        assertSame(plan, result.getMealPlan());
        assertEquals(MealType.DINNER, result.getMealType());
    }

    @Test
    void getOrCreateMealThrowsWhenPlanMissing() {
        when(mealRepository.findByMealPlanIdAndMealType(10L, MealType.LUNCH)).thenReturn(Optional.empty());
        when(mealPlanRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getOrCreateMeal(10L, MealType.LUNCH));
    }

    @Test
    void findByMealPlanIdDelegatesToRepository() {
        List<Meal> meals = List.of(new Meal(), new Meal());
        when(mealRepository.findByMealPlanId(10L)).thenReturn(meals);

        assertSame(meals, service.findByMealPlanId(10L));
        verify(mealRepository).findByMealPlanId(10L);
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
