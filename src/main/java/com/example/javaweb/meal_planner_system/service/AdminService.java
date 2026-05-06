package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.*;
import com.example.javaweb.meal_planner_system.entity.enums.FeedbackStatus;
import com.example.javaweb.meal_planner_system.entity.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    AdminStatsDTO getStatistics();
    Page<UserAccountDTO> getAllUsers(String keyword, UserStatus status, Pageable pageable);
    void updateUserStatus(Long userId, UserStatus status, Long adminId);
    Page<FeedbackDTO> getFeedbacks(FeedbackStatus status, Pageable pageable);
    void updateFeedbackStatus(Long feedbackId, FeedbackStatus status);

    // Phase 5: Admin enhancements
    UserAccountDTO getUserById(Long id);
    Page<DishDTO> getAllDishes(String keyword, Integer categoryId, Pageable pageable);
    DishDTO createAdminDish(AdminDishRequestDTO request);
    DishDTO updateAdminDish(Long id, AdminDishRequestDTO request);
    void deleteAdminDish(Long id);

    // UC16 NFR16-3: Admin audit logs
    Page<AdminAuditLogDTO> getAuditLogs(Pageable pageable);
}
