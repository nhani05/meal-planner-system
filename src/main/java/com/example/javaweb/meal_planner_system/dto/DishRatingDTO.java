package com.example.javaweb.meal_planner_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for DishRating
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishRatingDTO {
    private Long id;
    private Long accountId;
    private Long dishId;
    private Byte score;
    private String comment;
}
