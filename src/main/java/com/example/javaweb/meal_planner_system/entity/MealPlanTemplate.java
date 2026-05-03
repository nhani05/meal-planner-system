package com.example.javaweb.meal_planner_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Meal Plan Template Entity
 * Stores reusable meal plan templates
 */
@Entity
@Table(name = "tblMealPlanTemplate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlanTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private UserAccount account;

    @Column(name = "template_name", nullable = false, length = 200)
    private String templateName;

    @Column(name = "saved_at", nullable = false, updatable = false)
    private LocalDateTime savedAt;

    @PrePersist
    protected void onCreate() {
        savedAt = LocalDateTime.now();
    }
}
