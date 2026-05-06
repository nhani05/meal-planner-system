package com.example.javaweb.meal_planner_system.service.impl;

import com.example.javaweb.meal_planner_system.converter.MealPlanTemplateConverter;
import com.example.javaweb.meal_planner_system.dto.MealPlanTemplateDTO;
import com.example.javaweb.meal_planner_system.entity.MealPlan;
import com.example.javaweb.meal_planner_system.entity.MealPlanTemplate;
import com.example.javaweb.meal_planner_system.entity.UserAccount;
import com.example.javaweb.meal_planner_system.exception.BadRequestException;
import com.example.javaweb.meal_planner_system.exception.ResourceNotFoundException;
import com.example.javaweb.meal_planner_system.repository.MealPlanTemplateRepository;
import com.example.javaweb.meal_planner_system.service.MealPlanService;
import com.example.javaweb.meal_planner_system.service.MealPlanTemplateService;
import com.example.javaweb.meal_planner_system.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealPlanTemplateServiceImpl implements MealPlanTemplateService {

    @Autowired
    private MealPlanTemplateRepository mealPlanTemplateRepository;

    @Autowired
    private MealPlanService mealPlanService;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public List<MealPlanTemplateDTO> findByAccountId(Long accountId) {
        return mealPlanTemplateRepository.findByAccountId(accountId).stream()
                .map(MealPlanTemplateConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MealPlanTemplateDTO saveTemplate(Long accountId, Long sourcePlanId, String templateName) {
        MealPlan sourcePlan = mealPlanService.findById(sourcePlanId);
        if (sourcePlan == null || !sourcePlan.getAccount().getId().equals(accountId)) {
            throw new BadRequestException("Source meal plan not found or not owned by user");
        }

        UserAccount account = userAccountService.findById(accountId);
        MealPlanTemplate template = new MealPlanTemplate();
        template.setAccount(account);
        template.setTemplateName(templateName != null && !templateName.isBlank() ? templateName.trim() : sourcePlan.getPlanName());

        MealPlanTemplate saved = mealPlanTemplateRepository.save(template);
        return MealPlanTemplateConverter.toDTO(saved);
    }

    @Override
    public void deleteTemplate(Long templateId, Long accountId) {
        MealPlanTemplate template = mealPlanTemplateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found"));
        if (!template.getAccount().getId().equals(accountId)) {
            throw new BadRequestException("You do not own this template");
        }
        mealPlanTemplateRepository.delete(template);
    }
}
