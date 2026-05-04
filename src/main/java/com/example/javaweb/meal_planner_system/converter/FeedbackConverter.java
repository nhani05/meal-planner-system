package com.example.javaweb.meal_planner_system.converter;

import com.example.javaweb.meal_planner_system.dto.FeedbackDTO;
import com.example.javaweb.meal_planner_system.entity.UserFeedback;

public class FeedbackConverter {
    public static FeedbackDTO toDTO(UserFeedback entity) {
        if (entity == null) return null;
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(entity.getId());
        dto.setAccountId(entity.getAccount().getId());
        dto.setUsername(entity.getAccount().getUsername());
        dto.setContent(entity.getContent());
        dto.setStatus(entity.getStatus());
        dto.setSubmittedAt(entity.getSubmittedAt());
        return dto;
    }
}
