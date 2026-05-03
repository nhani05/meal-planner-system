package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.DishCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for DishCategory entity
 */
@Repository
public interface DishCategoryRepository extends JpaRepository<DishCategory, Integer> {
    Optional<DishCategory> findByName(String name);
    boolean existsByName(String name);
}
