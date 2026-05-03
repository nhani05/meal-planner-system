package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.NutritionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for NutritionInfo entity
 */
@Repository
public interface NutritionInfoRepository extends JpaRepository<NutritionInfo, Long> {
    Optional<NutritionInfo> findByDishId(Long dishId);
}
