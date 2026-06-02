package com.example.javaweb.meal_planner_system.entity;

import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import com.example.javaweb.meal_planner_system.entity.enums.UserStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityLifecycleTest {

    @Test
    void dishOnCreateSetsTimestampsAndDefaultSource() {
        Dish dish = new Dish();

        dish.onCreate();

        assertNotNull(dish.getCreatedAt());
        assertNotNull(dish.getUpdatedAt());
        assertEquals(DishSource.SYSTEM, dish.getSource());
    }

    @Test
    void dishOnUpdateRefreshesUpdatedAtOnly() {
        Dish dish = new Dish();
        LocalDateTime createdAt = LocalDateTime.of(2026, 6, 1, 1, 0);
        dish.setCreatedAt(createdAt);
        dish.setUpdatedAt(createdAt);

        dish.onUpdate();

        assertEquals(createdAt, dish.getCreatedAt());
        assertTrue(dish.getUpdatedAt().isAfter(createdAt));
    }

    @Test
    void entitiesWithCreatedAtSetItOnCreate() {
        DishCategory category = new DishCategory();
        DishRating rating = new DishRating();
        FavoriteDish favoriteDish = new FavoriteDish();
        Meal meal = new Meal();
        MealPlanTemplate template = new MealPlanTemplate();

        category.onCreate();
        rating.onCreate();
        favoriteDish.onCreate();
        meal.onCreate();
        template.onCreate();

        assertNotNull(category.getCreatedAt());
        assertNotNull(rating.getCreatedAt());
        assertNotNull(favoriteDish.getSavedAt());
        assertNotNull(meal.getCreatedAt());
        assertNotNull(template.getSavedAt());
    }

    @Test
    void entitiesWithUpdatedAtSetTimestampsOnCreateAndUpdate() {
        HealthGoal goal = new HealthGoal();
        HealthProfile profile = new HealthProfile();
        MealPlan plan = new MealPlan();
        NutritionInfo nutrition = new NutritionInfo();
        UserAccount account = new UserAccount();

        goal.onCreate();
        profile.onCreate();
        plan.onCreate();
        nutrition.onCreate();
        account.onCreate();

        assertNotNull(goal.getCreatedAt());
        assertNotNull(goal.getUpdatedAt());
        assertNotNull(profile.getUpdatedAt());
        assertNotNull(plan.getCreatedAt());
        assertNotNull(plan.getUpdatedAt());
        assertNotNull(nutrition.getUpdatedAt());
        assertNotNull(account.getCreatedAt());
        assertNotNull(account.getUpdatedAt());

        LocalDateTime oldGoalUpdatedAt = LocalDateTime.of(2026, 6, 1, 1, 0);
        goal.setUpdatedAt(oldGoalUpdatedAt);
        profile.setUpdatedAt(oldGoalUpdatedAt);
        plan.setUpdatedAt(oldGoalUpdatedAt);
        nutrition.setUpdatedAt(oldGoalUpdatedAt);
        account.setUpdatedAt(oldGoalUpdatedAt);

        goal.onUpdate();
        profile.onUpdate();
        plan.onUpdate();
        nutrition.onUpdate();
        account.onUpdate();

        assertTrue(goal.getUpdatedAt().isAfter(oldGoalUpdatedAt));
        assertTrue(profile.getUpdatedAt().isAfter(oldGoalUpdatedAt));
        assertTrue(plan.getUpdatedAt().isAfter(oldGoalUpdatedAt));
        assertTrue(nutrition.getUpdatedAt().isAfter(oldGoalUpdatedAt));
        assertTrue(account.getUpdatedAt().isAfter(oldGoalUpdatedAt));
    }

    @Test
    void ingredientOnCreateDefaultsUnitToGramOnlyWhenMissing() {
        Ingredient missingUnit = new Ingredient();
        Ingredient existingUnit = new Ingredient();
        existingUnit.setUnit("ml");

        missingUnit.onCreate();
        existingUnit.onCreate();

        assertEquals("g", missingUnit.getUnit());
        assertEquals("ml", existingUnit.getUnit());
    }

    @Test
    void passwordResetTokenOnCreateSetsCreatedAtAndDefaultUsedFlag() {
        PasswordResetToken missingUsed = new PasswordResetToken();
        PasswordResetToken existingUsed = new PasswordResetToken();
        existingUsed.setUsed(true);

        missingUsed.onCreate();
        existingUsed.onCreate();

        assertNotNull(missingUsed.getCreatedAt());
        assertFalse(missingUsed.getUsed());
        assertNotNull(existingUsed.getCreatedAt());
        assertTrue(existingUsed.getUsed());
    }

    @Test
    void userAccountLockoutMethodsTrackFailedAttempts() {
        UserAccount account = new UserAccount();
        account.setStatus(UserStatus.ACTIVE);

        assertFalse(account.isLocked());

        account.recordFailedLoginAttempt();
        account.recordFailedLoginAttempt();
        account.recordFailedLoginAttempt();
        account.recordFailedLoginAttempt();

        assertEquals(4, account.getFailedLoginAttempts());
        assertFalse(account.isLocked());

        account.recordFailedLoginAttempt();

        assertEquals(5, account.getFailedLoginAttempts());
        assertEquals(UserStatus.LOCKED, account.getStatus());
        assertTrue(account.isLocked());

        account.resetFailedLoginAttempts();

        assertEquals(0, account.getFailedLoginAttempts());
    }

    @Test
    void userAccountRecordFailedLoginAttemptHandlesNullCounter() {
        UserAccount account = new UserAccount();
        account.setStatus(UserStatus.ACTIVE);
        account.setFailedLoginAttempts(null);

        account.recordFailedLoginAttempt();

        assertEquals(1, account.getFailedLoginAttempts());
        assertEquals(UserStatus.ACTIVE, account.getStatus());
    }
}
