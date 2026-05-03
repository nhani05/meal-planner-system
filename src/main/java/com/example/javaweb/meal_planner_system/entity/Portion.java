package com.example.javaweb.meal_planner_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Portion Entity
 * Represents the quantity of a dish in a meal
 */
@Entity
@Table(name = "tblPortion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Portion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    @Column(name = "quantity_g", nullable = false, precision = 8, scale = 2)
    private BigDecimal quantityG;

    @Column(name = "calories_kcal", precision = 8, scale = 2)
    private BigDecimal caloriesKcal;

    @Column(name = "protein_g", precision = 7, scale = 2)
    private BigDecimal proteinG;

    @Column(name = "carb_g", precision = 7, scale = 2)
    private BigDecimal carbG;

    @Column(name = "fat_g", precision = 7, scale = 2)
    private BigDecimal fatG;
}
