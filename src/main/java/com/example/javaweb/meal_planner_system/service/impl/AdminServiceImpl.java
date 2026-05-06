package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.*;
import com.example.javaweb.meal_planner_system.dto.*;
import com.example.javaweb.meal_planner_system.entity.*;
import com.example.javaweb.meal_planner_system.entity.enums.*;
import com.example.javaweb.meal_planner_system.exception.BadRequestException;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.*;
import com.example.javaweb.meal_planner_system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private MealPlanRepository mealPlanRepository;

    @Autowired
    private UserFeedbackRepository userFeedbackRepository;

    @Autowired
    private DishCategoryRepository dishCategoryRepository;

    @Autowired
    private NutritionInfoRepository nutritionInfoRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private PortionRepository portionRepository;

    @Autowired
    private AdminAuditLogRepository adminAuditLogRepository;

    @Override
    public AdminStatsDTO getStatistics() {
        AdminStatsDTO stats = new AdminStatsDTO();
        stats.setTotalUsers(userAccountRepository.count());
        stats.setTotalDishes(dishRepository.count());
        stats.setActivePlansToday(mealPlanRepository.countByPlanDate(LocalDate.now()));
        // UC18: Count only PENDING feedbacks as "new"
        stats.setNewFeedbacks(userFeedbackRepository.countByStatus(FeedbackStatus.PENDING));
        return stats;
    }

    @Override
    public Page<UserAccountDTO> getAllUsers(String keyword, UserStatus status, Pageable pageable) {
        // UC16: Implement keyword and status filtering with LIKE search
        if (keyword != null && !keyword.isBlank()) {
            String searchTerm = keyword.trim();
            if (status != null) {
                return userAccountRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndStatus(
                        searchTerm, searchTerm, status, pageable)
                        .map(UserAccountConverter::toDTO);
            }
            return userAccountRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    searchTerm, searchTerm, pageable)
                    .map(UserAccountConverter::toDTO);
        }
        if (status != null) {
            return userAccountRepository.findByStatus(status, pageable)
                    .map(UserAccountConverter::toDTO);
        }
        return userAccountRepository.findAll(pageable)
                .map(UserAccountConverter::toDTO);
    }

    @Override
    public void updateUserStatus(Long userId, UserStatus status, Long adminId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserStatus oldStatus = user.getStatus();
        user.setStatus(status);
        userAccountRepository.save(user);

        // UC16 NFR16-3: Log admin action
        String action = switch (status) {
            case LOCKED -> "LOCK_USER";
            case ACTIVE -> "UNLOCK_USER";
            case DELETED -> "DELETE_USER";
            default -> "UPDATE_USER_STATUS";
        };
        logAdminAction(adminId, action, "UserAccount", userId,
                "Changed status from " + oldStatus + " to " + status);
    }

    private void logAdminAction(Long adminId, String action, String targetType, Long targetId, String details) {
        UserAccount admin = userAccountRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        AdminAuditLog log = new AdminAuditLog();
        log.setAdmin(admin);
        log.setAction(action);
        log.setTargetEntity(targetType);
        log.setTargetId(targetId);
        log.setDetails(details);
        adminAuditLogRepository.save(log);
    }

    @Override
    public Page<FeedbackDTO> getFeedbacks(FeedbackStatus status, Pageable pageable) {
        if (status != null) {
            return userFeedbackRepository.findByStatus(status, pageable)
                    .map(FeedbackConverter::toDTO);
        }
        return userFeedbackRepository.findAll(pageable)
                .map(FeedbackConverter::toDTO);
    }

    @Override
    public void updateFeedbackStatus(Long feedbackId, FeedbackStatus status) {
        UserFeedback feedback = userFeedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new ResourceNotFoundException("Feedback not found"));
        feedback.setStatus(status);
        userFeedbackRepository.save(feedback);
    }

    // ===================== Phase 5: Admin Enhancements =====================

    @Override
    public UserAccountDTO getUserById(Long id) {
        UserAccount user = userAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return UserAccountConverter.toDTO(user);
    }

    @Override
    public Page<DishDTO> getAllDishes(String keyword, Integer categoryId, Pageable pageable) {
        if (keyword != null && !keyword.isBlank() && categoryId != null) {
            return dishRepository.findByNameContainingIgnoreCaseAndCategoryId(keyword, categoryId, pageable)
                    .map(DishConverter::toDTO);
        }
        if (keyword != null && !keyword.isBlank()) {
            return dishRepository.findByNameContainingIgnoreCase(keyword, pageable)
                    .map(DishConverter::toDTO);
        }
        if (categoryId != null) {
            return dishRepository.findByCategoryId(categoryId, pageable)
                    .map(DishConverter::toDTO);
        }
        return dishRepository.findAll(pageable)
                .map(DishConverter::toDTO);
    }

    @Override
    @Transactional
    public DishDTO createAdminDish(AdminDishRequestDTO request) {
        DishDTO dishDTO = request.getDish();
        // Create Dish
        Dish dish = new Dish();
        dish.setName(dishDTO.getName());
        dish.setImageUrl(dishDTO.getImageUrl());
        dish.setSource(DishSource.SYSTEM);
        if (dishDTO.getCategoryId() != null) {
            dishCategoryRepository.findById(dishDTO.getCategoryId())
                    .ifPresent(dish::setCategory);
        }
        dish.setDifficulty(dishDTO.getDifficulty());
        dish.setTotalTimeMin(dishDTO.getTotalTimeMin());
        Dish savedDish = dishRepository.save(dish);

        // Create NutritionInfo
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

        // Create Ingredients
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

        return DishConverter.toDTO(savedDish);
    }

    @Override
    @Transactional
    public DishDTO updateAdminDish(Long id, AdminDishRequestDTO request) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found with id " + id));

        DishDTO dishDTO = request.getDish();
        dish.setName(dishDTO.getName());
        dish.setImageUrl(dishDTO.getImageUrl());
        if (dishDTO.getCategoryId() != null) {
            dishCategoryRepository.findById(dishDTO.getCategoryId())
                    .ifPresent(dish::setCategory);
        }
        dish.setDifficulty(dishDTO.getDifficulty());
        dish.setTotalTimeMin(dishDTO.getTotalTimeMin());
        Dish savedDish = dishRepository.save(dish);

        // Update NutritionInfo
        NutritionInfoDTO nutDTO = request.getNutrition();
        if (nutDTO != null) {
            NutritionInfo nutrition = nutritionInfoRepository.findByDishId(id)
                    .orElseGet(() -> {
                        NutritionInfo n = new NutritionInfo();
                        n.setDish(savedDish);
                        return n;
                    });
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

        // Replace Ingredients: delete old, create new
        List<IngredientDTO> ingredients = request.getIngredients();
        if (ingredients != null) {
            ingredientRepository.deleteAll(ingredientRepository.findByDishId(id));
            for (IngredientDTO ingDTO : ingredients) {
                Ingredient ingredient = new Ingredient();
                ingredient.setDish(savedDish);
                ingredient.setName(ingDTO.getName());
                ingredient.setQuantityG(ingDTO.getQuantityG());
                ingredient.setUnit(ingDTO.getUnit());
                ingredientRepository.save(ingredient);
            }
        }

        return DishConverter.toDTO(savedDish);
    }

    @Override
    @Transactional
    public void deleteAdminDish(Long id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found with id " + id));

        // Check if dish is used in any portions
        List<Portion> portions = portionRepository.findByDishId(id);
        if (!portions.isEmpty()) {
            throw new BadRequestException("Cannot delete dish: it is currently used in meal plans");
        }

        // Delete related entities first
        nutritionInfoRepository.findByDishId(id).ifPresent(nutritionInfoRepository::delete);
        List<Ingredient> ingredients = ingredientRepository.findByDishId(id);
        if (!ingredients.isEmpty()) {
            ingredientRepository.deleteAll(ingredients);
        }

        dishRepository.delete(dish);
    }

    @Override
    public Page<AdminAuditLogDTO> getAuditLogs(Pageable pageable) {
        return adminAuditLogRepository.findAllByOrderByPerformedAtDesc(pageable)
                .map(log -> {
                    AdminAuditLogDTO dto = new AdminAuditLogDTO();
                    dto.setId(log.getId());
                    dto.setAdminId(log.getAdmin().getId());
                    dto.setAdminUsername(log.getAdmin().getUsername());
                    dto.setAction(log.getAction());
                    dto.setTargetEntity(log.getTargetEntity());
                    dto.setTargetId(log.getTargetId());
                    dto.setDetails(log.getDetails());
                    dto.setPerformedAt(log.getPerformedAt());
                    return dto;
                });
    }
}
