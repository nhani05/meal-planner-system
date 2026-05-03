package com.example.javaweb.meal_planner_system.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for Portion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortionDTO {
    private Long id;
    private Long mealId;
    private Long dishId;
    private BigDecimal quantityG;
    private BigDecimal caloriesKcal;
    private BigDecimal proteinG;
    private BigDecimal carbG;
    private BigDecimal fatG;
}
