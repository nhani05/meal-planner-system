package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for MealPlan entity
 */
@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByAccountId(Long accountId);
    List<MealPlan> findByAccountIdAndPlanDateBetween(Long accountId, LocalDate startDate, LocalDate endDate);
    Optional<MealPlan> findByAccountIdAndPlanDate(Long accountId, LocalDate planDate);
}
