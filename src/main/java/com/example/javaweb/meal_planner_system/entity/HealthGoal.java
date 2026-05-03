package com.example.javaweb.meal_planner_system.entity;

import com.example.javaweb.meal_planner_system.entity.enums.ActivityLevel;
import com.example.javaweb.meal_planner_system.entity.enums.GoalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Health Goal Entity
 * Defines user's health goals and nutritional targets
 */
@Entity
@Table(name = "tblHealthGoal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private UserAccount account;

    @Convert(converter = com.example.javaweb.meal_planner_system.entity.converter.EnumConverters.GoalTypeConverter.class)
    @Column(name = "goal_type", nullable = false)
    private GoalType goalType;

    @Convert(converter = com.example.javaweb.meal_planner_system.entity.converter.EnumConverters.ActivityLevelConverter.class)
    @Column(name = "activity_level", nullable = false)
    private ActivityLevel activityLevel;

    @Column(name = "target_weight_kg", precision = 5, scale = 2)
    private BigDecimal targetWeightKg;

    @Column(name = "daily_calories_kcal")
    private Integer dailyCaloriesKcal;

    @Column(name = "protein_g_day", precision = 6, scale = 2)
    private BigDecimal proteinGDay;

    @Column(name = "carb_g_day", precision = 6, scale = 2)
    private BigDecimal carbGDay;

    @Column(name = "fat_g_day", precision = 6, scale = 2)
    private BigDecimal fatGDay;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
