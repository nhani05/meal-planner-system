package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.FeedbackConverter;
import com.example.javaweb.meal_planner_system.converter.UserAccountConverter;
import com.example.javaweb.meal_planner_system.dto.AdminStatsDTO;
import com.example.javaweb.meal_planner_system.dto.FeedbackDTO;
import com.example.javaweb.meal_planner_system.dto.UserAccountDTO;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.entity.UserFeedback;
import com.example.javaweb.meal_planner_system.entity.enums.FeedbackStatus;
import com.example.javaweb.meal_planner_system.entity.enums.UserStatus;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.*;
import com.example.javaweb.meal_planner_system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    @Override
    public AdminStatsDTO getStatistics() {
        AdminStatsDTO stats = new AdminStatsDTO();
        stats.setTotalUsers(userAccountRepository.count());
        stats.setTotalDishes(dishRepository.count());
        stats.setActivePlansToday(mealPlanRepository.countByPlanDate(LocalDate.now()));
        // Giả sử có query đếm feedback mới (PENDING)
        stats.setNewFeedbacks(userFeedbackRepository.count()); // Tạm thời đếm tất cả
        return stats;
    }

    @Override
    public Page<UserAccountDTO> getAllUsers(String keyword, UserStatus status, Pageable pageable) {
        // Tạm thời trả về tất cả, logic filter keyword/status có thể bổ sung sau
        return userAccountRepository.findAll(pageable)
                .map(UserAccountConverter::toDTO);
    }

    @Override
    public void updateUserStatus(Long userId, UserStatus status) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatus(status);
        userAccountRepository.save(user);
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
}
