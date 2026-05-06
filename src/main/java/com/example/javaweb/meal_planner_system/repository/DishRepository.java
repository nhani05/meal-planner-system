package com.example.javaweb.meal_planner_system.repository;

import com.example.javaweb.meal_planner_system.entity.Dish;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    Page<Dish> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Dish> findByNameContainingIgnoreCaseAndCategoryId(String name, Integer categoryId, Pageable pageable);
    Page<Dish> findByCategoryId(Integer categoryId, Pageable pageable);
    boolean existsByCategoryId(Integer categoryId);

    @Query("SELECT d FROM Dish d " +
           "LEFT JOIN d.category c " +
           "LEFT JOIN NutritionInfo n ON n.dish.id = d.id " +
           "WHERE (:keyword IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
           "AND (:categoryId IS NULL OR c.id = :categoryId) " +
           "AND (:minCal IS NULL OR n.caloriesPer100g >= :minCal) " +
           "AND (:maxCal IS NULL OR n.caloriesPer100g <= :maxCal)")
    Page<Dish> searchDishes(@Param("keyword") String keyword,
                            @Param("categoryId") Integer categoryId,
                            @Param("minCal") BigDecimal minCal,
                            @Param("maxCal") BigDecimal maxCal,
                            Pageable pageable);
}
