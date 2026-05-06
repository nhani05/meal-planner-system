package com.example.javaweb.meal_planner_system.service;

import com.example.javaweb.meal_planner_system.dto.MealPlanTemplateDTO;

import java.util.List;

/**
 * Service interface for MealPlanTemplate
 */
public interface MealPlanTemplateService {
    List<MealPlanTemplateDTO> findByAccountId(Long accountId);
    MealPlanTemplateDTO saveTemplate(Long accountId, Long sourcePlanId, String templateName);
    MealPlanTemplateDTO updateTemplate(Long templateId, Long accountId, String templateName);
    void deleteTemplate(Long templateId, Long accountId);
}
