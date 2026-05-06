package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.FeedbackDTO;

import java.util.List;

public interface UserFeedbackService {
    FeedbackDTO submitFeedback(Long accountId, String content);
    List<FeedbackDTO> getMyFeedbacks(Long accountId);
}
