package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.Portion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Portion entity
 */
@Repository
public interface PortionRepository extends JpaRepository<Portion, Long> {
    List<Portion> findByMealId(Long mealId);
    List<Portion> findByDishId(Long dishId);
}
