package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.MealPlanTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for MealPlanTemplate entity
 */
@Repository
public interface MealPlanTemplateRepository extends JpaRepository<MealPlanTemplate, Long> {
    List<MealPlanTemplate> findByAccountId(Long accountId);
}
