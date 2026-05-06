package com.example.javaweb.meal_planner_system.dto;

import com.example.javaweb.meal_planner_system.entity.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealUpdateDTO {
    private MealType mealType;
    private List<PortionUpdateDTO> portions;
}
