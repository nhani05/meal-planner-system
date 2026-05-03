package com.example.javaweb.meal_planner_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Nutrition Info Entity
 * Stores nutritional information for dishes (per 100g)
 */
@Entity
@Table(name = "tblNutritionInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NutritionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "dish_id", nullable = false, unique = true)
    private Dish dish;

    @Column(name = "calories_per_100g", precision = 7, scale = 2, nullable = false)
    private BigDecimal caloriesPer100g;

    @Column(name = "protein_per_100g", precision = 6, scale = 2, nullable = false)
    private BigDecimal proteinPer100g;

    @Column(name = "carb_per_100g", precision = 6, scale = 2, nullable = false)
    private BigDecimal carbPer100g;

    @Column(name = "fat_per_100g", precision = 6, scale = 2, nullable = false)
    private BigDecimal fatPer100g;

    @Column(name = "fiber_per_100g", precision = 6, scale = 2)
    private BigDecimal fiberPer100g;

    @Column(name = "sat_fat_per_100g", precision = 6, scale = 2)
    private BigDecimal satFatPer100g;

    @Column(name = "vitamin_a_mcg", precision = 8, scale = 2)
    private BigDecimal vitaminAMcg;

    @Column(name = "vitamin_c_mg", precision = 8, scale = 2)
    private BigDecimal vitaminCMg;

    @Column(name = "vitamin_d_mcg", precision = 8, scale = 2)
    private BigDecimal vitaminDMcg;

    @Column(name = "calcium_mg", precision = 8, scale = 2)
    private BigDecimal calciumMg;

    @Column(name = "iron_mg", precision = 8, scale = 2)
    private BigDecimal ironMg;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
