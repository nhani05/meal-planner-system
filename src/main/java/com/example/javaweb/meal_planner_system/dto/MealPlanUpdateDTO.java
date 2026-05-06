package com.example.javaweb.meal_planner_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlanUpdateDTO {
    private String planName;
    private LocalDate planDate;
    private List<MealUpdateDTO> meals;
}
