package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Dish entity
 */
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findBySource(DishSource source);
    List<Dish> findBySourceAndCategoryId(DishSource source, Integer categoryId);
    List<Dish> findByAccountId(Long accountId);
    List<Dish> findBySourceAndAccountId(DishSource source, Long accountId);
    boolean existsByName(String name);
}
