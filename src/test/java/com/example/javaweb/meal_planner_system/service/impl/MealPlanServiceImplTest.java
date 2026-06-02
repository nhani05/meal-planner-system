package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.dto.MealPlanDTO;
import com.example.javaweb.meal_planner_system.dto.MealPlanUpdateDTO;
import com.example.javaweb.meal_planner_system.dto.MealUpdateDTO;
import com.example.javaweb.meal_planner_system.dto.PortionUpdateDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.Meal;
import com.example.javaweb.meal_planner_system.entity.MealPlan;
import com.example.javaweb.meal_planner_system.entity.NutritionInfo;
import com.example.javaweb.meal_planner_system.entity.Portion;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import com.example.javaweb.meal_planner_system.exception.BadRequestException;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.DishRepository;
import com.example.javaweb.meal_planner_system.repository.MealPlanRepository;
import com.example.javaweb.meal_planner_system.repository.MealRepository;
import com.example.javaweb.meal_planner_system.repository.NutritionInfoRepository;
import com.example.javaweb.meal_planner_system.repository.PortionRepository;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MealPlanServiceImplTest {

    private MealPlanRepository mealPlanRepository;
    private UserAccountService userAccountService;
    private MealRepository mealRepository;
    private PortionRepository portionRepository;
    private DishRepository dishRepository;
    private NutritionInfoRepository nutritionInfoRepository;
    private MealPlanServiceImpl service;

    @BeforeEach
    void setUp() throws Exception {
        mealPlanRepository = mock(MealPlanRepository.class);
        userAccountService = mock(UserAccountService.class);
        mealRepository = mock(MealRepository.class);
        portionRepository = mock(PortionRepository.class);
        dishRepository = mock(DishRepository.class);
        nutritionInfoRepository = mock(NutritionInfoRepository.class);
        service = new MealPlanServiceImpl();
        setField(service, "mealPlanRepository", mealPlanRepository);
        setField(service, "userAccountService", userAccountService);
        setField(service, "mealRepository", mealRepository);
        setField(service, "portionRepository", portionRepository);
        setField(service, "dishRepository", dishRepository);
        setField(service, "nutritionInfoRepository", nutritionInfoRepository);
    }

    @Test
    void saveFindAndDeleteDelegateToRepositoryWithValidation() {
        MealPlan plan = mealPlan(1L, "Plan", LocalDate.of(2026, 6, 2));
        when(mealPlanRepository.save(plan)).thenReturn(plan);
        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(plan));
        when(mealPlanRepository.findById(2L)).thenReturn(Optional.empty());

        assertSame(plan, service.save(plan));
        assertSame(plan, service.findById(1L));
        assertThrows(BadRequestException.class, () -> service.findById(null));
        assertThrows(ResourceNotFoundException.class, () -> service.findById(2L));

        service.delete(1L);
        verify(mealPlanRepository).deleteById(1L);
    }

    @Test
    void findQueriesDelegateToRepository() {
        LocalDate date = LocalDate.of(2026, 6, 2);
        LocalDate start = date.minusDays(1);
        LocalDate end = date.plusDays(1);
        MealPlan plan = mealPlan(1L, "Plan", date);
        when(mealPlanRepository.findByAccountId(9L)).thenReturn(List.of(plan));
        when(mealPlanRepository.findByAccountIdAndPlanDateBetween(9L, start, end)).thenReturn(List.of(plan));
        when(mealPlanRepository.findByAccountIdAndPlanDate(9L, date)).thenReturn(Optional.of(plan));

        assertEquals(1, service.findByAccountId(9L).size());
        assertEquals(1, service.findByAccountIdAndDateBetween(9L, start, end).size());
        assertSame(plan, service.findByAccountIdAndPlanDate(9L, date).orElseThrow());
    }

    @Test
    void createForAccountBuildsPlanFromDto() {
        UserAccount account = new UserAccount();
        account.setId(4L);
        MealPlanDTO input = new MealPlanDTO(null, "New plan", LocalDate.of(2026, 6, 2));
        when(userAccountService.findById(4L)).thenReturn(account);
        when(mealPlanRepository.save(any(MealPlan.class))).thenAnswer(invocation -> {
            MealPlan saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        MealPlanDTO result = service.createForAccount(4L, input);

        assertEquals(10L, result.getId());
        assertEquals("New plan", result.getPlanName());
        assertEquals(LocalDate.of(2026, 6, 2), result.getPlanDate());
    }

    @Test
    void updateFromDTOUpdatesFieldsReplacesMealsAndCalculatesNutrition() {
        MealPlan plan = mealPlan(1L, "Old", LocalDate.of(2026, 6, 1));
        Meal oldMeal = new Meal();
        oldMeal.setId(2L);
        Dish dish = new Dish();
        dish.setId(3L);
        NutritionInfo nutrition = nutrition("100.00", "10.00", "20.00", "5.00");
        MealPlanUpdateDTO input = new MealPlanUpdateDTO(
                "Updated",
                LocalDate.of(2026, 6, 2),
                List.of(new MealUpdateDTO(MealType.DINNER, List.of(new PortionUpdateDTO(3L, new BigDecimal("250.00")))))
        );

        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(plan));
        when(mealPlanRepository.save(plan)).thenReturn(plan);
        when(mealRepository.findByMealPlanId(1L)).thenReturn(List.of(oldMeal));
        when(portionRepository.findByMealId(2L)).thenReturn(List.of(new Portion()));
        when(mealRepository.save(any(Meal.class))).thenAnswer(invocation -> {
            Meal saved = invocation.getArgument(0);
            saved.setId(8L);
            return saved;
        });
        when(dishRepository.findById(3L)).thenReturn(Optional.of(dish));
        when(nutritionInfoRepository.findByDishId(3L)).thenReturn(Optional.of(nutrition));

        MealPlanDTO result = service.updateFromDTO(1L, input);

        assertEquals(1L, result.getId());
        assertEquals("Updated", result.getPlanName());
        assertEquals(LocalDate.of(2026, 6, 2), result.getPlanDate());
        verify(portionRepository).deleteAll(any());
        verify(mealRepository).deleteAll(List.of(oldMeal));

        ArgumentCaptor<Portion> captor = ArgumentCaptor.forClass(Portion.class);
        verify(portionRepository).save(captor.capture());
        Portion savedPortion = captor.getValue();
        assertEquals(new BigDecimal("250.00"), savedPortion.getQuantityG());
        assertEquals(new BigDecimal("250.00"), savedPortion.getCaloriesKcal());
        assertEquals(new BigDecimal("25.00"), savedPortion.getProteinG());
        assertEquals(new BigDecimal("50.00"), savedPortion.getCarbG());
        assertEquals(new BigDecimal("12.50"), savedPortion.getFatG());
    }

    @Test
    void updateFromDTODefaultsMealTypeAndThrowsWhenDishMissing() {
        MealPlan plan = mealPlan(1L, "Old", LocalDate.of(2026, 6, 1));
        MealPlanUpdateDTO input = new MealPlanUpdateDTO(
                null,
                null,
                List.of(new MealUpdateDTO(null, List.of(new PortionUpdateDTO(99L, new BigDecimal("100.00")))))
        );

        when(mealPlanRepository.findById(1L)).thenReturn(Optional.of(plan));
        when(mealRepository.findByMealPlanId(1L)).thenReturn(List.of());
        when(mealRepository.save(any(Meal.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(dishRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateFromDTO(1L, input));

        ArgumentCaptor<Meal> captor = ArgumentCaptor.forClass(Meal.class);
        verify(mealRepository).save(captor.capture());
        assertEquals(MealType.BREAKFAST, captor.getValue().getMealType());
    }

    private static MealPlan mealPlan(Long id, String name, LocalDate date) {
        MealPlan plan = new MealPlan();
        plan.setId(id);
        plan.setPlanName(name);
        plan.setPlanDate(date);
        return plan;
    }

    private static NutritionInfo nutrition(String calories, String protein, String carb, String fat) {
        NutritionInfo nutrition = new NutritionInfo();
        nutrition.setCaloriesPer100g(new BigDecimal(calories));
        nutrition.setProteinPer100g(new BigDecimal(protein));
        nutrition.setCarbPer100g(new BigDecimal(carb));
        nutrition.setFatPer100g(new BigDecimal(fat));
        return nutrition;
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
