package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.DishRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for DishRating entity
 */
@Repository
public interface DishRatingRepository extends JpaRepository<DishRating, Long> {
    List<DishRating> findByDishId(Long dishId);
    List<DishRating> findByAccountId(Long accountId);
    Optional<DishRating> findByAccountIdAndDishId(Long accountId, Long dishId);
    
    @Query("SELECT AVG(dr.score) FROM DishRating dr WHERE dr.dish.id = ?1")
    Double getAverageRatingByDishId(Long dishId);
    
    @Query("SELECT COUNT(dr) FROM DishRating dr WHERE dr.dish.id = ?1")
    Long getCountByDishId(Long dishId);
}
