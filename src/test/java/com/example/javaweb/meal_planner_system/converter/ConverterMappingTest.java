package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.dto.DishCategoryDTO;
import com.example.javaweb.meal_planner_system.dto.DishRatingDTO;
import com.example.javaweb.meal_planner_system.dto.FeedbackDTO;
import com.example.javaweb.meal_planner_system.dto.HealthGoalDTO;
import com.example.javaweb.meal_planner_system.dto.HealthProfileDTO;
import com.example.javaweb.meal_planner_system.dto.MealDTO;
import com.example.javaweb.meal_planner_system.dto.MealPlanDTO;
import com.example.javaweb.meal_planner_system.dto.MealPlanTemplateDTO;
import com.example.javaweb.meal_planner_system.dto.PortionDTO;
import com.example.javaweb.meal_planner_system.dto.UserAccountDTO;
import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.DishCategory;
import com.example.javaweb.meal_planner_system.entity.DishRating;
import com.example.javaweb.meal_planner_system.entity.HealthGoal;
import com.example.javaweb.meal_planner_system.entity.HealthProfile;
import com.example.javaweb.meal_planner_system.entity.Meal;
import com.example.javaweb.meal_planner_system.entity.MealPlan;
import com.example.javaweb.meal_planner_system.entity.MealPlanTemplate;
import com.example.javaweb.meal_planner_system.entity.Portion;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.entity.UserFeedback;
import com.example.javaweb.meal_planner_system.entity.enums.ActivityLevel;
import com.example.javaweb.meal_planner_system.entity.enums.FeedbackStatus;
import com.example.javaweb.meal_planner_system.entity.enums.Gender;
import com.example.javaweb.meal_planner_system.entity.enums.GoalType;
import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import com.example.javaweb.meal_planner_system.entity.enums.UserRole;
import com.example.javaweb.meal_planner_system.entity.enums.UserStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ConverterMappingTest {

    @Test
    void simpleConvertersReturnNullForNullInput() {
        assertNull(DishCategoryConverter.toDTO(null));
        assertNull(DishRatingConverter.toDTO(null));
        assertNull(FeedbackConverter.toDTO(null));
        assertNull(HealthGoalConverter.toDTO(null));
        assertNull(HealthProfileConverter.toDTO(null));
        assertNull(MealConverter.toDTO(null));
        assertNull(MealPlanConverter.toDTO(null));
        assertNull(MealPlanTemplateConverter.toDTO(null));
        assertNull(PortionConverter.toDTO(null));
        assertNull(UserAccountConverter.toDTO(null));
    }

    @Test
    void dishCategoryConverterMapsFields() {
        DishCategory category = new DishCategory();
        category.setId(4);
        category.setName("Vietnamese");

        DishCategoryDTO dto = DishCategoryConverter.toDTO(category);

        assertEquals(4, dto.getId());
        assertEquals("Vietnamese", dto.getName());
    }

    @Test
    void dishRatingConverterMapsNestedIdsAndFields() {
        UserAccount account = user(8L);
        Dish dish = dish(9L);
        DishRating rating = new DishRating();
        rating.setId(3L);
        rating.setAccount(account);
        rating.setDish(dish);
        rating.setScore((byte) 5);
        rating.setComment("Good");

        DishRatingDTO dto = DishRatingConverter.toDTO(rating);

        assertEquals(3L, dto.getId());
        assertEquals(8L, dto.getAccountId());
        assertEquals(9L, dto.getDishId());
        assertEquals((byte) 5, dto.getScore());
        assertEquals("Good", dto.getComment());
    }

    @Test
    void feedbackConverterMapsAccountAndStatusFields() {
        LocalDateTime submittedAt = LocalDateTime.of(2026, 6, 2, 8, 0);
        UserFeedback feedback = new UserFeedback();
        feedback.setId(7L);
        feedback.setAccount(user(2L, "tester"));
        feedback.setContent("Need more recipes");
        feedback.setStatus(FeedbackStatus.PROCESSING);
        feedback.setSubmittedAt(submittedAt);

        FeedbackDTO dto = FeedbackConverter.toDTO(feedback);

        assertEquals(7L, dto.getId());
        assertEquals(2L, dto.getAccountId());
        assertEquals("tester", dto.getUsername());
        assertEquals("Need more recipes", dto.getContent());
        assertEquals(FeedbackStatus.PROCESSING, dto.getStatus());
        assertEquals(submittedAt, dto.getSubmittedAt());
    }

    @Test
    void healthGoalConverterMapsNutritionTargets() {
        HealthGoal goal = new HealthGoal();
        goal.setId(5L);
        goal.setGoalType(GoalType.WEIGHT_LOSS);
        goal.setActivityLevel(ActivityLevel.MODERATE);
        goal.setTargetWeightKg(new BigDecimal("60.50"));
        goal.setDailyCaloriesKcal(1800);
        goal.setProteinGDay(new BigDecimal("120.00"));
        goal.setCarbGDay(new BigDecimal("200.00"));
        goal.setFatGDay(new BigDecimal("50.00"));

        HealthGoalDTO dto = HealthGoalConverter.toDTO(goal);

        assertEquals(5L, dto.getId());
        assertEquals(GoalType.WEIGHT_LOSS, dto.getGoalType());
        assertEquals(ActivityLevel.MODERATE, dto.getActivityLevel());
        assertEquals(new BigDecimal("60.50"), dto.getTargetWeightKg());
        assertEquals(1800, dto.getDailyCaloriesKcal());
        assertEquals(new BigDecimal("120.00"), dto.getProteinGDay());
        assertEquals(new BigDecimal("200.00"), dto.getCarbGDay());
        assertEquals(new BigDecimal("50.00"), dto.getFatGDay());
    }

    @Test
    void healthProfileConverterMapsFields() {
        HealthProfile profile = new HealthProfile();
        profile.setId(12L);
        profile.setFullName("Nguyen Van A");
        profile.setAge(28);
        profile.setGender(Gender.MALE);
        profile.setHeightCm(new BigDecimal("170.50"));
        profile.setWeightKg(new BigDecimal("65.25"));
        profile.setAvatarUrl("avatar.png");

        HealthProfileDTO dto = HealthProfileConverter.toDTO(profile);

        assertEquals(12L, dto.getId());
        assertEquals("Nguyen Van A", dto.getFullName());
        assertEquals(28, dto.getAge());
        assertEquals(Gender.MALE, dto.getGender());
        assertEquals(new BigDecimal("170.50"), dto.getHeightCm());
        assertEquals(new BigDecimal("65.25"), dto.getWeightKg());
        assertEquals("avatar.png", dto.getAvatarUrl());
    }

    @Test
    void mealAndMealPlanConvertersMapFields() {
        MealPlan plan = new MealPlan();
        plan.setId(4L);
        plan.setPlanName("Weekday plan");
        plan.setPlanDate(LocalDate.of(2026, 6, 2));

        Meal meal = new Meal();
        meal.setId(6L);
        meal.setMealPlan(plan);
        meal.setMealType(MealType.LUNCH);

        MealDTO mealDto = MealConverter.toDTO(meal);
        MealPlanDTO planDto = MealPlanConverter.toDTO(plan);

        assertEquals(6L, mealDto.getId());
        assertEquals(4L, mealDto.getMealPlanId());
        assertEquals(MealType.LUNCH, mealDto.getMealType());
        assertEquals(4L, planDto.getId());
        assertEquals("Weekday plan", planDto.getPlanName());
        assertEquals(LocalDate.of(2026, 6, 2), planDto.getPlanDate());
    }

    @Test
    void mealPlanTemplateConverterMapsFields() {
        LocalDateTime savedAt = LocalDateTime.of(2026, 6, 1, 12, 30);
        MealPlanTemplate template = new MealPlanTemplate();
        template.setId(11L);
        template.setTemplateName("High protein");
        template.setSavedAt(savedAt);

        MealPlanTemplateDTO dto = MealPlanTemplateConverter.toDTO(template);

        assertEquals(11L, dto.getId());
        assertEquals("High protein", dto.getTemplateName());
        assertEquals(savedAt, dto.getSavedAt());
    }

    @Test
    void portionConverterMapsNestedIdsAndNutritionFields() {
        Meal meal = new Meal();
        meal.setId(15L);

        Portion portion = new Portion();
        portion.setId(20L);
        portion.setMeal(meal);
        portion.setDish(dish(30L));
        portion.setQuantityG(new BigDecimal("250.00"));
        portion.setCaloriesKcal(new BigDecimal("450.00"));
        portion.setProteinG(new BigDecimal("35.00"));
        portion.setCarbG(new BigDecimal("55.00"));
        portion.setFatG(new BigDecimal("12.00"));

        PortionDTO dto = PortionConverter.toDTO(portion);

        assertEquals(20L, dto.getId());
        assertEquals(15L, dto.getMealId());
        assertEquals(30L, dto.getDishId());
        assertEquals(new BigDecimal("250.00"), dto.getQuantityG());
        assertEquals(new BigDecimal("450.00"), dto.getCaloriesKcal());
        assertEquals(new BigDecimal("35.00"), dto.getProteinG());
        assertEquals(new BigDecimal("55.00"), dto.getCarbG());
        assertEquals(new BigDecimal("12.00"), dto.getFatG());
    }

    @Test
    void userAccountConverterMapsPublicAccountFields() {
        UserAccount account = user(18L, "admin");
        account.setEmail("admin@example.com");
        account.setRole(UserRole.ADMIN);
        account.setStatus(UserStatus.ACTIVE);

        UserAccountDTO dto = UserAccountConverter.toDTO(account);

        assertEquals(18L, dto.getId());
        assertEquals("admin", dto.getUsername());
        assertEquals("admin@example.com", dto.getEmail());
        assertEquals(UserRole.ADMIN, dto.getRole());
        assertEquals(UserStatus.ACTIVE, dto.getStatus());
    }

    private static Dish dish(Long id) {
        Dish dish = new Dish();
        dish.setId(id);
        return dish;
    }

    private static UserAccount user(Long id) {
        return user(id, "user" + id);
    }

    private static UserAccount user(Long id, String username) {
        UserAccount account = new UserAccount();
        account.setId(id);
        account.setUsername(username);
        return account;
    }
}
