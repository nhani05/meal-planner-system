package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.FeedbackConverter;
import com.example.javaweb.meal_planner_system.dto.FeedbackDTO;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.entity.UserFeedback;
import com.example.javaweb.meal_planner_system.entity.enums.FeedbackStatus;
import com.example.javaweb.meal_planner_system.exception.BadRequestException;
import com.example.javaweb.meal_planner_system.repository.UserFeedbackRepository;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import com.example.javaweb.meal_planner_system.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFeedbackServiceImpl implements UserFeedbackService {

    @Autowired
    private UserFeedbackRepository userFeedbackRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public FeedbackDTO submitFeedback(Long accountId, String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new BadRequestException("Content is required");
        }

        UserAccount account = userAccountService.findById(accountId);

        UserFeedback feedback = new UserFeedback();
        feedback.setAccount(account);
        feedback.setContent(content.trim());
        feedback.setStatus(FeedbackStatus.PENDING);

        UserFeedback saved = userFeedbackRepository.save(feedback);
        return FeedbackConverter.toDTO(saved);
    }

    @Override
    public List<FeedbackDTO> getMyFeedbacks(Long accountId) {
        return userFeedbackRepository.findByAccountId(accountId).stream()
                .map(FeedbackConverter::toDTO)
                .collect(Collectors.toList());
    }
}
