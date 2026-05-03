package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.FavoriteDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for FavoriteDish entity
 */
@Repository
public interface FavoriteDishRepository extends JpaRepository<FavoriteDish, Long> {
    List<FavoriteDish> findByAccountId(Long accountId);
    Optional<FavoriteDish> findByAccountIdAndDishId(Long accountId, Long dishId);
    boolean existsByAccountIdAndDishId(Long accountId, Long dishId);
    void deleteByAccountIdAndDishId(Long accountId, Long dishId);
}
