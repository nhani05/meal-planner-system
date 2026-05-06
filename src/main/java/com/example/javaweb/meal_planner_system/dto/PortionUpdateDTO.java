package com.example.javaweb.meal_planner_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortionUpdateDTO {
    private Long dishId;
    private BigDecimal quantityG;
}
