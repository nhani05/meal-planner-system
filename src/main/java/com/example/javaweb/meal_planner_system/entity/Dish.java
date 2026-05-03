package com.example.javaweb.meal_planner_system.entity;

import com.example.javaweb.meal_planner_system.entity.enums.DishDifficulty;
import com.example.javaweb.meal_planner_system.entity.enums.DishSource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Dish Entity
 * Represents a dish/recipe with its metadata
 */
@Entity
@Table(name = "tblDish")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private DishCategory category;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DishSource source;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private UserAccount account;

    @Enumerated(EnumType.STRING)
    private DishDifficulty difficulty;

    @Column(name = "total_time_min")
    private Integer totalTimeMin;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (source == null) {
            source = DishSource.SYSTEM;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
